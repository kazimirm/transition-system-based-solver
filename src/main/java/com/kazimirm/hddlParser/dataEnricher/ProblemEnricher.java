package com.kazimirm.hddlParser.dataEnricher;

import com.kazimirm.hddlParser.hddlObjects.*;
import com.microsoft.z3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;
import java.util.stream.Collectors;

public class ProblemEnricher {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private Domain domain;
    private Problem problem;
    private List<Predicate> predicates = new ArrayList<>();
    private HashMap<String, List<Argument>> objectsToTypedLists = new HashMap<>(); // for each type creates list with such objects
    private HashMap<String, Type> typeNameToType = new HashMap<>();
    private HashMap<String, Integer> objectToInt = new HashMap<>();
    //private List<FuncDecl> functions = new ArrayList<>();
    private HashMap<String, FuncDecl> functions = new HashMap<>();


    Context ctx = new Context();
    Fixedpoint fix;
    List<List<BoolExpr>> predicatesExpressionsList = new ArrayList<>();

    public ProblemEnricher(Domain domain, Problem problem) {
        this.domain = domain;
        this.problem = problem;

    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public Problem enrichProblem() {
        fix = ctx.mkFixedpoint();
        enrichProblemObjects();
        enrichPredicates();
        enrichAbstractTasks();
        enrichPrimitiveTasks();
        enrichInitialTask();
        encodeVariables();
        encodeTasks();
        encodeActions();
        encodeMethods();
        return problem;
    }

    /**
     * This method takes given domain and problem and creates variables for all ground instances of predicate
     */
    private void enrichPredicates() {

        for (Type t : domain.getTypes()) {
            objectsToTypedLists.put(t.getName(), new ArrayList<>());
            typeNameToType.put(t.getName(), t);
        }

        for (Argument a : problem.getObjects()) {

            String type = a.getType();
            objectsToTypedLists.get(type).add(a);
            String baseType = typeNameToType.get(type).getBaseType();

            if (objectsToTypedLists.containsKey(baseType)) {
                objectsToTypedLists.get(baseType).add(a);
            }
        }

        for (Predicate p : domain.getPredicates()) {
            List<List<Argument>> lists = new ArrayList<>();
            for (Argument a : p.getArguments()) {
                lists.add(objectsToTypedLists.get(a.getType()));
            }
            generatePermutationsForPredicate(p, lists);
        }

        logger.debug("PREDICATES: ");
        logger.debug(predicates.stream().map(Predicate::toStringWithoutIndex)
                .collect(Collectors.joining(", ")));

    }

    /**
     * This method is used to enhance all predicates. We calculate all permutations of given predicate and check
     * predicates from init block of problem (problem.getInit() which returns all predicates from problem -
     * all these predicates are true.) If the predicate is not there -> is false.
     *
     * @param p     - predicate
     * @param lists - list of List<Argument>. The size of list is equal to number of arguments to predicate.
     */
    private void generatePermutationsForPredicate(Predicate p, List<List<Argument>> lists) {
        generatePermutationsForPredicate(p, lists, 0, "");
    }

    /**
     * @param p       - predicate
     * @param lists   - list of List<Argument>. The size of list is equal to number of arguments to predicate.
     * @param depth   - field for recursion
     * @param current - field for recursion
     */
    private void generatePermutationsForPredicate(Predicate p, List<List<Argument>> lists, int depth, String current) {
        if (depth == lists.size()) {
            Predicate predicate = new Predicate();
            predicate.setName(p.getName());
            List<String> argsNames = Arrays.asList(current.split(";"));
            List<Argument> args = new ArrayList<>();
            for (int i = 0; i < p.getArguments().size(); i++) {
                Argument a = new Argument();
                a.setName(argsNames.get(i));
                a.setType(p.getArguments().get(i).getType());
                args.add(a);
            }
            predicate.setArguments(args);

            if (problem.getInit().contains(predicate)) {
                predicate.setValue(true);
            } else {
                predicate.setValue(false);
            }

            predicates.add(predicate);
            return;
        }

        for (int i = 0; i < lists.get(depth).size(); i++) {
            generatePermutationsForPredicate(p, lists, depth + 1, current + lists.get(depth).get(i).getName() + ";");
        }
    }

    /**
     * We need to create int variables for every ground variable. Number of each var instance must be equal to max + 1 number of
     * maximum subtasks in method. Then we will create list of lists which will be used in tasks/methods/... encoding
     */
    private void encodeVariables(){
        int max = 0;
        for (Method m : domain.getMethods()){
            if (m.getSubtasks().size() > max){
                max = m.getSubtasks().size();
            }
        }

        for (Predicate p : predicates) {
            List<BoolExpr> vars = new ArrayList<>();
            for (int i = 0; i <= max; i++) {
                BoolExpr var = ctx.mkBoolConst(p.toStringWithoutIndex() + "[" + i + "]");
                vars.add(var);
            }
            predicatesExpressionsList.add(vars);
        }
    }

    private void encodeTasks() {
        for (Task t : domain.getTasks() ) {
            List<Sort> params = new ArrayList<>();

            // objects
            for (Parameter p : t.getParameters()) {
                IntSort param = ctx.mkIntSort();
                logger.debug("IntSort: " + param);
                params.add(param);
            }

            // preConditions
            for (Predicate p : predicates) {
                BoolSort param = ctx.mkBoolSort();
                logger.debug("BoolSort: " + param);
                params.add(param);
            }

            //postConditions
            for (Predicate p : predicates) {
                BoolSort param = ctx.mkBoolSort();
                logger.debug("BoolSort: " + param);
                params.add(param);
            }

            Sort[] sort = params.toArray(new Sort[0]);

            BoolSort returnValue = ctx.mkBoolSort();

            FuncDecl f = ctx.mkFuncDecl(t.getName(), sort, returnValue);
            functions.put(t.getName(), f);
            fix.registerRelation(f);
            logger.debug(f.getSExpr());
        }
    }

    private void encodeActions() {
        for (Action a : domain.getActions()) {
            List<Sort> params = new ArrayList<>();

            // objects
            for (Parameter p : a.getParameters()) {
                IntSort param = ctx.mkIntSort();
                logger.debug("IntSort: " + param);
                params.add(param);
            }

            // preConditions
            for (Predicate p : predicates) {
                BoolSort param = ctx.mkBoolSort();
                logger.debug("BoolSort: " + param);
                params.add(param);
            }

            // postConditions
            for (Predicate p : predicates) {
                BoolSort param = ctx.mkBoolSort();
                logger.debug("BoolSort: " + param);
                params.add(param);
            }

            Sort[] sort = params.toArray(new Sort[0]);

            BoolSort returnValue = ctx.mkBoolSort();

            FuncDecl f = ctx.mkFuncDecl(a.getName(), sort, returnValue);
            functions.put(a.getName(), f);
            fix.registerRelation(f);
            logger.debug(f.getSExpr());
        }
    }

    private void encodeMethods() {
        for (Method m : domain.getMethods()) {
            HashMap<String, IntExpr> intExpressions = new HashMap<>();
            List<Expr> subtaskExpressions = new ArrayList<>();

            for (Parameter p : m.getParameters()){
                IntExpr intExpr = ctx.mkIntConst(p.getName());
                intExpressions.put(p.getName(), intExpr);
            }

            for (Subtask subtask : m.getSubtasks()){
                List<Expr> params = new ArrayList<>();

                for (Parameter p : subtask.getTask().getParameters()) {
                    IntExpr param = intExpressions.get(p.getName());
                    logger.debug("IntExpression: " + param);
                    params.add(param);
                }

                for (List<BoolExpr> boolExprList : predicatesExpressionsList) {
                    BoolExpr param = boolExprList.get(m.getSubtasks().indexOf(subtask));
                    logger.debug("BoolExpression of preCondition: " + param);
                    params.add(param);
                }

                for (List<BoolExpr> boolExprList : predicatesExpressionsList) {
                    BoolExpr param = boolExprList.get(m.getSubtasks().indexOf(subtask) + 1);
                    logger.debug("BoolExpression of postCondition: " + param);
                    params.add(param);
                }

                Expr[] expr = params.toArray(new Expr[0]);
                logger.debug(expr.toString());
                Expr subtaskExpr = ctx.mkApp(functions.get(subtask.getTask().getName()), expr);
                subtaskExpressions.add(subtaskExpr);
            }

            List<Expr> params = new ArrayList<>();
            for (Parameter p : m.getTask().getParameters()) {
                IntExpr param = intExpressions.get(p.getName());
                logger.debug("IntExpression: " + param);
                params.add(param);
            }

            for (List<BoolExpr> boolExprList : predicatesExpressionsList) {
                BoolExpr param = boolExprList.get(0);
                logger.debug("BoolExpression of preCondition: " + param);
                params.add(param);
            }

            for (List<BoolExpr> boolExprList : predicatesExpressionsList) {
                BoolExpr param = boolExprList.get(m.getSubtasks().size());
                logger.debug("BoolExpression of postCondition: " + param);
                params.add(param);
            }

            Expr[] expr = params.toArray(new Expr[0]);
            logger.debug(expr.toString());
            Expr taskExpr = ctx.mkApp(functions.get(m.getTask().getName()), expr);

            Expr[] subtasks = subtaskExpressions.toArray(new Expr[0]);
            Expr methodImplication = ctx.mkImplies(ctx.mkAnd(subtasks), taskExpr);
            logger.debug(methodImplication.toString());
        }
    }

    private void enrichAbstractTasks() {
        // New predicates variables preparation
        logger.debug("ABSTRACT TASKS(TASKS):");
        List<Predicate> predicatesVariables = cloneList(predicates);
        for (Predicate p : predicatesVariables) {
            p.setValue(null);
        }

        for (Method m : domain.getMethods()) {
            Task t = m.getTask();
            List<Predicate> preConditions = cloneList(predicatesVariables);
            preConditions.forEach(p -> p.setIndex(0));
            List<Predicate> postConditions = cloneList(predicatesVariables);
            postConditions.forEach(p -> p.setIndex(m.getSubtasks().size()));
            t.setPreConditions(preConditions);
            t.setPostConditions(postConditions);
            String task = t.toString();
            List<String> subtasks = new ArrayList<>();

            for (Subtask s : m.getSubtasks()) {
                Task st = s.getTask();
                List<Predicate> subTaskPreConditions = cloneList(predicatesVariables);
                subTaskPreConditions.forEach(p -> p.setIndex(m.getSubtasks().indexOf(s)));
                List<Predicate> subTaskPostConditions = cloneList(predicatesVariables);
                subTaskPostConditions.forEach(p -> p.setIndex(m.getSubtasks().indexOf(s) + 1));
                st.setPreConditions(subTaskPreConditions);
                st.setPostConditions(subTaskPostConditions);
                subtasks.add(st.toString());
            }


//            List<Sort> params = new ArrayList<>();
//
//            for (Parameter p:t.getParameters()){
//                IntSort param = ctx.mkIntSort();
//                logger.debug("IntSort: " + param);
//                params.add(param);
//            }
//
//            for (Predicate p:predicates){
//                BoolSort param = ctx.mkBoolSort();
//                logger.debug("IntSort: " + param);
//                params.add(param);
//            }
//
//            Sort[] sort =  params.toArray(new Sort[0]);
//
//            BoolSort returnValue = ctx.mkBoolSort();
//
//            FuncDecl f = ctx.mkFuncDecl(t.getName(), sort, returnValue);
//            //fix.registerRelation();
//
//            //FuncDecl f = ctx.mkConstDecl(t.getName(), ctx.mkBoolSort());
//            fix.registerRelation(f);
//            logger.debug(f.getSExpr());
//            //logger.debug(fix.getRules());
            String abstractTask = (task + ":- " + System.getProperty("line.separator") + subtasks.stream().map(Object::toString).
                    collect(Collectors.joining(" ∧ " + System.getProperty("line.separator"))));
            logger.debug(abstractTask);
        }
    }

    /**
     *
     */
    private void enrichPrimitiveTasks() {

        logger.debug("PRIMITIVE TASKS(ACTIONS):");

        List<Predicate> preConditions = cloneList(predicates);
        preConditions.forEach(p -> p.setIndex(0));
        List<Predicate> postConditions = cloneList(predicates);
        postConditions.forEach(p -> p.setIndex(1));

        for (Action action : domain.getActions()) {

            for (Predicate p : action.getPreconditions()) {
                p.setIndex(0);
            }

            for (Predicate p : action.getEffects()) {
                p.setIndex(1);
            }

            List<List<Argument>> lists = new ArrayList<>();
            for (Parameter p : action.getParameters()) {
                lists.add(objectsToTypedLists.get(p.getType()));
            }
            generatePermutationsForActionParameters(action, lists, 0, "");

            for (List<Parameter> permutation : action.getParameterPermutations()) {
                List<Integer> params = permutation.stream().map(p -> objectToInt.get(p.getName())).collect(Collectors.toList());

                logger.debug(action.getName() + "(" + params.stream().map(Object::toString).collect(Collectors.joining(", ")) + ", " +
                        preConditions.stream().map(Predicate::toString)
                                .collect(Collectors.joining(", ")) + ", " +
                        postConditions.stream().map(Predicate::toString)
                                .collect(Collectors.joining(", ")) + ") :-"
                );
                logger.debug(action.getConcretePredicates(action.getPreconditions(), permutation).stream().map(Predicate::toStringWithOptionalNegation).
                        collect(Collectors.joining(" ∧ ")) + " ∧ ");
                logger.debug(action.getConcretePredicates(action.getEffects(), permutation).stream().map(Predicate::toStringWithOptionalNegation).
                        collect(Collectors.joining(" ∧ ")));
            }

        }
    }

    /**
     * @param a       - Action
     * @param lists   - list of List<Argument>. The size of list is equal to number of arguments to predicate.
     * @param depth   - field for recursion
     * @param current - field for recursion
     */
    private void generatePermutationsForActionParameters(Action a, List<List<Argument>> lists, int depth, String current) {
        if (depth == lists.size()) {
            List<String> argsNames = Arrays.asList(current.split(";"));
            List<Parameter> params = new ArrayList<>();
            for (int i = 0; i < a.getParameters().size(); i++) {
                Parameter p = new Parameter();
                p.setName(argsNames.get(i));
                p.setType(a.getParameters().get(i).getType());
                params.add(p);
            }

            a.getParameterPermutations().add(params);
            return;
        }

        for (int i = 0; i < lists.get(depth).size(); i++) {
            generatePermutationsForActionParameters(a, lists, depth + 1, current + lists.get(depth).get(i).getName() + ";");
        }
    }

    /**
     * Encoding of all problem object into an unique integer representation, because of need to use num in Z3.
     */
    private void enrichProblemObjects() {
        int i = 0;
        for (Argument a : problem.getObjects()) {
            objectToInt.put(a.getName(), i);
            i++;
        }
    }

    public List<Predicate> cloneList(List<Predicate> list) {
        try {
            List<Predicate> clone = new ArrayList<Predicate>(list.size());
            for (Predicate item : list) clone.add(item.clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            logger.error("List {} cannot be cloned", list);
            return null;
        }
    }

    private void enrichInitialTask() {
        logger.debug("INITIAL TASKS:");

        logger.debug("⊥ :- " + predicates.stream().map(Predicate::toStringWithOptionalNegation)
                .collect(Collectors.joining(" ∧ ")) + " ∧");

        for (Subtask subtask : problem.getHtn().getSubtasks()) {

            List<Predicate> preConditions = cloneList(predicates);
            preConditions.forEach(p -> p.setIndex(0));
            List<Predicate> postConditions = cloneList(predicates);
            postConditions.forEach(p -> p.setIndex(1));

            List<Integer> params = subtask.getTask().getParameters().stream().map(p -> objectToInt.get(p.getName())).collect(Collectors.toList());

            logger.debug(subtask.getName() + "(" + params.stream().map(Object::toString).collect(Collectors.joining(", ")) + ", " +
                    preConditions.stream().map(Predicate::toString)
                            .collect(Collectors.joining(", ")) + ", " +
                    postConditions.stream().map(Predicate::toString)
                            .collect(Collectors.joining(", ")) + ")"
            );
        }
    }
}
