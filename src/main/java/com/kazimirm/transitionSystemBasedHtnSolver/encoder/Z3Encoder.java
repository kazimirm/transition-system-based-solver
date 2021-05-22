package com.kazimirm.transitionSystemBasedHtnSolver.encoder;

import com.kazimirm.transitionSystemBasedHtnSolver.graphRepresentation.Graph;
import com.kazimirm.transitionSystemBasedHtnSolver.graphRepresentation.Node;
import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.*;
import com.microsoft.z3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Z3Encoder {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private Domain domain;
    private Problem problem;

    private LinkedHashMap<String, Integer> objectToInt;
    private List<Expr> allExpressions = new ArrayList<>();
    private HashMap<String, FuncDecl> functions = new HashMap<>();
    private List<Predicate> predicates;
    private HashMap<String, List<BoolExpr>> predicatesExpressionsList = new HashMap<>();
    private Graph graph = new Graph(true);

    private Context ctx = new Context();
    private Fixedpoint fix;
    private Expr answer;
    private int MAX_NUMBER_OF_SUBTASKS;

    public Z3Encoder(Domain domain, Problem problem) {
        this.domain = domain;
        this.problem = problem;
        objectToInt = problem.getObjectToInt();
        predicates = problem.getPredicates();
    }

    public void encodeToZ3ExpressionsAndGetResult() {
        fix = ctx.mkFixedpoint();

        Params params = ctx.mkParams();
        params.add("xform.slice", false);
        params.add("xform.inline_linear", false);
        params.add("xform.inline_eager", false);
        fix.setParameters(params);

        encodeVariables();
        encodeFunctionSignatures();
        encodeActions();
        encodeMethods();
        encodeHtnAndInit();

        //visualizeGraph();
        createGraph();
        graph.printStandardOutput();

        System.out.println();
    }

    /**
     * We need to create bool variables for every ground variable. Number of each var instance must be equal to max + 1 number of
     * maximum subtasks in method. Then we will create list of lists which will be used in tasks/methods/... encoding
     */
    private void encodeVariables() {
        int max = 0;
        for (Method m : domain.getMethods()) {
            if (m.getSubtasks().size() > max) {
                max = m.getSubtasks().size();
            }
        }

        if (problem.getHtn().getSubtasks().size() > max) {
            max = problem.getHtn().getSubtasks().size();
        }

        MAX_NUMBER_OF_SUBTASKS = max;

        for (Predicate p : predicates) {
            List<BoolExpr> vars = new ArrayList<>();
            for (int i = 0; i <= MAX_NUMBER_OF_SUBTASKS; i++) {
                BoolExpr var = ctx.mkBoolConst(p.toStringWithoutIndex() + "[" + i + "]");
                vars.add(var);
            }
            predicatesExpressionsList.put(p.toString(), vars);
        }
    }

    /**
     * Foreach task & action we need to declare function signature with all its parameters
     */
    private void encodeFunctionSignatures() {
        for (Task task : domain.getTasks()) {
            for (int i = 0; i < MAX_NUMBER_OF_SUBTASKS; i++) {
                declareRelations(task.getName() + "#" + i, task.getParameters());
            }
        }

        for (Action action : domain.getActions()) {
            for (int i = 0; i < MAX_NUMBER_OF_SUBTASKS; i++) {
                declareRelations(action.getName() + "#" + i, action.getParameters());
            }
        }
    }

    /**
     * Encoding actions into Z3 rules and adding them to FixedPoint - fix object
     */
    private void encodeActions() {
        for (Action a : domain.getActions()) {

            for (HashMap<String, Parameter> permutation : a.getParameterPermutations()) {

                List<String> allUnchangedPredicates = new ArrayList<>(predicatesExpressionsList.keySet());
                List<BoolExpr> ruleAParams = new ArrayList<>();
                List<Expr> intConsts = new ArrayList<>();
                List<Expr> ruleBParams = new ArrayList<>();

                // Preconditions
                for (Predicate p : a.getPreconditions()) {
                    String predicate = getConcretePredicate(p, permutation);
                    BoolExpr expr = null;
                    try {
                        expr = predicatesExpressionsList.get(predicate).get(0);
                    } catch(NullPointerException e) {
                        logger.error("missing expression predicate, probably error in setting types and subtypes.");
                    }
                    if (p.getValue() == true) {
                        ruleAParams.add(expr);
                    } else {
                        ruleAParams.add(ctx.mkNot(expr));
                    }
                }

                // Effects
                for (Predicate p : a.getEffects()) {
                    String predicate = getConcretePredicate(p, permutation);
                    BoolExpr expr = predicatesExpressionsList.get(predicate).get(1);
                    if (p.getValue() == true) {
                        ruleAParams.add(expr);
                    } else {
                        ruleAParams.add(ctx.mkNot(expr));
                    }
                    allUnchangedPredicates.remove(predicate);
                }

                // Predicates which are not affected by the actions should have the same value ([0] == [1])
                for (String predicate : allUnchangedPredicates) {
                    BoolExpr exprPrecondition = predicatesExpressionsList.get(predicate).get(0);
                    BoolExpr exprEffect = predicatesExpressionsList.get(predicate).get(1);
                    BoolExpr expr = ctx.mkEq(exprPrecondition, exprEffect);
                    ruleAParams.add(expr);
                }

                // Adding int constants (concrete objects)
                for (Parameter p : permutation.values()) {
                    IntNum intNum = ctx.mkInt(objectToInt.get(p.getName()));
                    intConsts.add(intNum);
                }

                // All predicates before action
                for (List<BoolExpr> boolExprList : predicatesExpressionsList.values()) {
                    BoolExpr param = boolExprList.get(0);
                    ruleBParams.add(param);
                }

                // All predicates after action
                for (List<BoolExpr> boolExprList : predicatesExpressionsList.values()) {
                    BoolExpr param = boolExprList.get(1);
                    ruleBParams.add(param);
                }

                Expr[] ruleBExpr = Stream.concat(intConsts.stream(), ruleBParams.stream()).collect(Collectors.toList()).toArray(new Expr[0]);


                for (int i = 0; i < MAX_NUMBER_OF_SUBTASKS; i++) {
                    Expr ruleA = ctx.mkAnd(ruleAParams.toArray(new Expr[0]));
                    Expr ruleB = ctx.mkApp(functions.get(a.getName() + "#" + i), ruleBExpr);

                    Expr expr = ctx.mkImplies(ruleA, ruleB);
                    allExpressions.add(expr);
                    Quantifier quantifier = ctx.mkForall(ruleBParams.toArray(new Expr[0]), expr, 0, null, null, null, null);

                    Symbol symbol = ctx.mkSymbol(a.getName() + "#" + i + permutation.toString());
                    fix.addRule(quantifier, symbol);
                }
                //logger.debug("Action - " + a.getName() + ":   " + expr.toString());
            }
        }
    }

    /**
     * @param p           - predicate
     * @param permutation - permutation of parameters
     * @return String representation of concrete predicate with concrete parameters
     */
    private String getConcretePredicate(Predicate p, HashMap<String, Parameter> permutation) {
        Predicate concretePredicate = new Predicate();
        concretePredicate.setName(p.getName());
        List<Argument> args = new ArrayList<>();
        for (Argument arg : p.getArguments()) {
            Argument concreteArg = permutation.get(arg.getName());
            args.add(concreteArg);
        }
        concretePredicate.setArguments(args);

        String predicate = concretePredicate.toString();
        return predicate;
    }

    /**
     * Encoding HTN and INIT state into Z3 rule and adding it to FixedPoint - fix object
     */
    private void encodeHtnAndInit() {
        List<Expr> subtaskExpressions = new ArrayList<>();
        List<Expr> boolPredicates = new ArrayList<>();

        List<String> allPredicates = new ArrayList<>(predicatesExpressionsList.keySet());
        List<Subtask> subtasks = problem.getHtn().getSubtasks();

        for (Subtask subtask : subtasks) {
            List<Expr> params = new ArrayList<>();

            // int objects
            for (Parameter p : subtask.getTask().getParameters()) {
                IntNum intNum = ctx.mkInt(objectToInt.get(p.getName()));
                params.add(intNum);
            }

            // preConditions
            for (List<BoolExpr> boolExprList : predicatesExpressionsList.values()) {
                BoolExpr param = boolExprList.get(subtasks.indexOf(subtask));
                boolPredicates.add(param);
                params.add(param);
            }

            // postConditions
            for (List<BoolExpr> boolExprList : predicatesExpressionsList.values()) {
                BoolExpr param = boolExprList.get(subtasks.indexOf(subtask) + 1);
                boolPredicates.add(param);
                params.add(param);
            }

            Expr[] expr = params.toArray(new Expr[0]);
            Expr subtaskExpr = ctx.mkApp(functions.get(subtask.getTask().getName() + "#" + subtasks.indexOf(subtask)), expr);
            subtaskExpressions.add(subtaskExpr);
        }

        for (Predicate p : problem.getInit()) {
            String name = p.toStringWithoutIndex() + "[0]";
            BoolExpr var = predicatesExpressionsList.get(name).get(0);
            subtaskExpressions.add(var);
            allPredicates.remove(name);
        }

        for (String p : allPredicates) {
            BoolExpr var = predicatesExpressionsList.get(p).get(0);
            var = ctx.mkNot(var);
            subtaskExpressions.add(var);
        }

        Expr[] rule = subtaskExpressions.toArray(new Expr[0]);
        Expr init = ctx.mkAnd(rule);
        allExpressions.add(init);

        FuncDecl goal = ctx.mkFuncDecl("Goal", new Sort[0], ctx.mkBoolSort());
        fix.registerRelation(goal);

        Expr mainGoal = ctx.mkApp(goal);
        Expr impl = ctx.mkImplies(init, mainGoal);

        Quantifier quant = ctx.mkForall(boolPredicates.toArray(new Expr[0]), impl, 0, null, null, null, null);

        fix.addRule(quant, ctx.mkSymbol("INIT"));
        fix.query(mainGoal);
        answer = fix.getAnswer();
        //logger.debug("INIT:   " + impl.toString());
    }

    /**
     * This method is used to declare function signature for each task & action and stores into functions hashmap.
     * (Two loops for preconditions and postconditions can be merged but are separated for better readability and understanding)
     *
     * @param name      - action/task/... name
     * @param arguments - int objects
     */
    private void declareRelations(String name, List<Parameter> arguments) {

        List<Sort> params = new ArrayList<>();

        // objects
        for (Parameter p : arguments) {
            IntSort param = ctx.mkIntSort();
            params.add(param);
        }

        // preConditions
        for (Predicate p : predicates) {
            BoolSort param = ctx.mkBoolSort();
            params.add(param);
        }

        // postConditions
        for (Predicate p : predicates) {
            BoolSort param = ctx.mkBoolSort();
            params.add(param);
        }

        Sort[] sort = params.toArray(new Sort[0]);

        for (int i = 0; i < MAX_NUMBER_OF_SUBTASKS; i++) {
            BoolSort returnValue = ctx.mkBoolSort();
            FuncDecl f = ctx.mkFuncDecl(name, sort, returnValue);
            functions.put(name, f);
            fix.registerRelation(f);
        }
        //logger.debug(f.getSExpr());
    }

    /**
     * For each domain method - implication rule is created. If all method subtasks are satisfiable, method task is satisfiable.
     * Firstly we create expression of conjunctions for each subtask, then using this expression implication for task is created.
     */
    private void encodeMethods() {
        for (Method m : domain.getMethods()) {
            HashMap<String, IntExpr> intExpressions = new HashMap<>();
            List<Expr> subtaskExpressions = new ArrayList<>();

            for (Parameter param : m.getParameters()) {
                IntExpr intExpr = ctx.mkIntConst(param.getName());
                intExpressions.put(param.getName(), intExpr);
            }

            List<Subtask> subtasks = m.getSubtasks();
            List<Expr> boolPredicates = new ArrayList<>();
            for (Subtask subtask : subtasks) {
                List<Expr> params = new ArrayList<>();

                // Int objects
                for (Parameter p : subtask.getTask().getParameters()) {
                    IntExpr param = intExpressions.get(p.getName());
                    boolPredicates.add(param);
                    params.add(param);
                }

                // Preconditions
                for (List<BoolExpr> boolExprList : predicatesExpressionsList.values()) {
                    BoolExpr param = boolExprList.get(subtasks.indexOf(subtask));
                    boolPredicates.add(param);
                    params.add(param);
                }

                // Effects
                for (List<BoolExpr> boolExprList : predicatesExpressionsList.values()) {
                    BoolExpr param = boolExprList.get(subtasks.indexOf(subtask) + 1);
                    boolPredicates.add(param);
                    params.add(param);
                }

                Expr[] expr = params.toArray(new Expr[0]);
                Expr subtaskExpr = ctx.mkApp(functions.get(subtask.getTask().getName() + "#" + subtasks.indexOf(subtask)), expr);
                subtaskExpressions.add(subtaskExpr);
            }

            for (Constraint c : m.getConstraints()){
                // to distinct int objects are not equal
                IntExpr param1 = intExpressions.get(c.getParam1().getName());
                IntExpr param2 = intExpressions.get(c.getParam2().getName());
                Expr eq = ctx.mkNot(ctx.mkEq(param1, param2));
                subtaskExpressions.add(eq);
            }

            List<Expr> params = new ArrayList<>();

            // Int objects
            for (Parameter p : m.getTask().getParameters()) {
                IntExpr param = intExpressions.get(p.getName());
                params.add(param);
            }

            // Preconditions
            for (List<BoolExpr> boolExprList : predicatesExpressionsList.values()) {
                BoolExpr param = boolExprList.get(0);
                params.add(param);
            }

            // Effects
            for (List<BoolExpr> boolExprList : predicatesExpressionsList.values()) {
                BoolExpr param = boolExprList.get(subtasks.size());
                params.add(param);
            }

            for (int i = 0; i < MAX_NUMBER_OF_SUBTASKS; i++){
                Expr subtasksConjunction = ctx.mkAnd(subtaskExpressions.toArray(new Expr[0]));
                Expr taskExpr = ctx.mkApp(functions.get(m.getTask().getName() + "#" + i), params.toArray(new Expr[0]));
                Expr methodImplication = ctx.mkImplies(subtasksConjunction, taskExpr);
                Quantifier quantifier = ctx.mkForall(boolPredicates.toArray(new Expr[0]), methodImplication, 0, null, null, null, null);

                Symbol symbol = ctx.mkSymbol(m.toString() + "#" + i);
                fix.addRule(quantifier, symbol);
                allExpressions.add(methodImplication);
            }
            //logger.debug("Method: " + methodImplication.toString());
        }
    }

    private void visualizeGraph() {
        StringBuilder sb = new StringBuilder();
        Expr root = answer.getArgs()[0];
        List<List<Expr>> expressions = new ArrayList<>();
        expressions.add(Arrays.asList(root));
        int i = 0;
        HashMap<Integer, Expr> exprHashMap = new HashMap<>();
        exprHashMap.put(root.hashCode(), root);

        sb.append("digraph " + problem.getName() + " {").append(System.getProperty("line.separator"));

        while (expressions.get(i) != null && !expressions.get(i).isEmpty()) {
            List<Expr> newLevel = new ArrayList<>();
            for (Expr e : expressions.get(i)) {
                String label = getExpressionLabel(e);
                if (!(e instanceof BoolExpr) && i != 0){
                    String color;
                    if ("Z3_OP_PR_HYPER_RESOLVE".equals(e.getFuncDecl().getDeclKind().name())){
                        color = "red";
                        //if (!label.contains("_Precondition#")) {
                            sb.append(label + "[color=" + color + ", ordering=out];").append(System.getProperty("line.separator"));
                        //}
                    }
                }

                if (e.getNumArgs() >= 1) {
                    Arrays.stream(e.getArgs()).filter(arg -> !(arg instanceof BoolExpr))
                            .sorted(Comparator.comparingInt(expr -> getOrderOfTask(getExpressionLabel(expr))))
                            .collect(Collectors.toCollection(() -> newLevel));
                    List<Expr> sorted = Arrays.stream(e.getArgs()).sorted(Comparator.comparingInt(expr -> getOrderOfTask(getExpressionLabel(expr)))).collect(Collectors.toList());

                    for (Expr arg : sorted){
                         exprHashMap.put(arg.hashCode(), arg);
                        if (!(arg instanceof BoolExpr) && ("Z3_OP_PR_HYPER_RESOLVE".equals(arg.getFuncDecl().getDeclKind().name()))) {
                           //     &&
                           // !label.contains("_Precondition#") && !getExpressionLabel(arg).contains("_Precondition#") && i != 0) {
                            sb.append(e.hashCode() + " -> " + arg.hashCode() + ";").append(System.getProperty("line.separator"));
                        }
                    }

                }
            }
            expressions.add(newLevel);
            i++;
        }

        sb.append("graph [labelloc=\"b\" labeljust=\"r\" label=<\n" +
                "\t<TABLE BORDER=\"0\" CELLBORDER=\"2\" CELLSPACING=\"0\">\n" +
                "\t<TR><TD colspan=\"2\">Objects Legend</TD></TR>").append(System.getProperty("line.separator"));
        for (Map.Entry<String, Integer> value : objectToInt.entrySet()) {
            sb.append("<TR><TD>" + value.getValue() + "</TD><TD>" + value.getKey() + "</TD></TR>").append(System.getProperty("line.separator"));
        }
        sb.append("</TABLE>>];\n}").append(System.getProperty("line.separator"));
        System.out.println(sb.toString());

    }

    private void createGraph() {
        Expr root = answer.getArgs()[0];
        List<List<Expr>> expressions = new ArrayList<>();
        expressions.add(Arrays.asList(root));
        int i = 0;
        int order = 0;
        HashMap<Integer, Expr> exprHashMap = new HashMap<>();
        exprHashMap.put(root.hashCode(), root);

        graph.setIntFromInversemap(objectToInt);
        HashMap<Integer, Node> expressionHashToNode = new HashMap<>();
        Node goal = null;

        while (expressions.get(i) != null && !expressions.get(i).isEmpty()) {
            List<Expr> newLevel = new ArrayList<>();
            for (Expr e : expressions.get(i)) {

                if (e.getNumArgs() >= 1) {
                    Arrays.stream(e.getArgs()).filter(arg -> !(arg instanceof BoolExpr))
                            .sorted(Comparator.comparingInt(expr -> getOrderOfTask(getExpressionLabel(expr))))
                            .collect(Collectors.toCollection(() -> newLevel));

                    List<Expr> sorted = Arrays.stream(e.getArgs()).sorted(Comparator.comparingInt(expr -> getOrderOfTask(getExpressionLabel(expr)))).collect(Collectors.toList());

                    for (Expr arg : sorted){
                        exprHashMap.put(arg.hashCode(), arg);
                        if (!(arg instanceof BoolExpr) && ("Z3_OP_PR_HYPER_RESOLVE".equals(arg.getFuncDecl().getDeclKind().name())) && i != 0) {

                            if (expressionHashToNode.get(e.hashCode()) == null){
                                Node node = createNode(e, order++);
                                expressionHashToNode.put(e.hashCode(), node);
                                if ("Goal".equals(node.getName())){
                                    graph.setRoot(node);
                                }
                            }

                            Node node = createNode(arg, order++);
                            expressionHashToNode.put(arg.hashCode(), node);

                            graph.addEdge(expressionHashToNode.get(e.hashCode()), expressionHashToNode.get(arg.hashCode()));
                        }
                    }

                }
            }
            expressions.add(newLevel);
            i++;
        }
    }

    private String getExpressionLabel(Expr e){
        String name = "";
        if (!(e instanceof BoolExpr) && "Z3_OP_PR_HYPER_RESOLVE".equals(e.getFuncDecl().getDeclKind().name())){
        name =  (e.getArgs()[e.getNumArgs() - 1]).toString()
                .replace("true", "").replace("false", "")
                .replace("\n", "").replace("\r", "").trim().replaceAll(" +", " ");
        }
        String description = e.getNumArgs() > 2 ? "method" : "action";
        return e.hashCode() + " [label=\"" + name + "\"]" + "[description=\"" + description + "\"]";
    }

    private Node createNode(Expr e, int order){
        String name = "";
        if (!(e instanceof BoolExpr) && "Z3_OP_PR_HYPER_RESOLVE".equals(e.getFuncDecl().getDeclKind().name())){
            name =  (e.getArgs()[e.getNumArgs() - 1]).toString()
                    .replace("true", "").replace("false", "")
                    .replace("\n", "").replace("\r", "").trim().replaceAll(" +", " ");
        }
        TaskType type = e.getNumArgs() > 2 ? TaskType.METHOD :  TaskType.ACTION;
        Node node = new Node();
        node.setName(name);
        node.setType(type);
        node.setN(order);
        return node;
    }

    private int getOrderOfTask(String label){
        int order = 0;
        try{
            order = Integer.parseInt(label.substring(label.lastIndexOf('#') + 1, label.lastIndexOf('|')));
        } catch (Exception e){
            return order;
        }
        return order;
    }

}
