package com.kazimirm.transitionSystemBasedHtnSolver.answerExtractor.graphRepresentation;

import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.TaskType;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Expr;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {

    // Each node maps to a list of all his neighbors
    private HashMap<Node, LinkedList<Node>> adjacencyMap;
    private final boolean directed = true;
    private HashMap<Integer, String> intToObject;
    private HashMap<String, Integer> objectToInt;
    private Expr answer;
    private Node root;
    private String problemName;
    private final String RELEVANT_NODE = "Z3_OP_PR_HYPER_RESOLVE";
    private final String METHOD_PRECONDITION_SUFFIX = "_Precondition#";


    public Graph(String problemName, HashMap<String, Integer> objectToInt, Expr answer) {
        this.problemName = problemName;
        this.objectToInt = objectToInt;
        this.answer = answer;
        setIntToObjectFromInversemap(objectToInt);
        adjacencyMap = new HashMap<>();
        createGraph();
    }

    private void createGraph() {
        Expr root = answer.getArgs()[0];
        List<List<Expr>> expressions = new ArrayList<>();
        expressions.add(Arrays.asList(root));
        int i = 0;
        int order = 0;
        HashMap<Integer, Expr> exprHashMap = new HashMap<>();
        exprHashMap.put(root.hashCode(), root);
        HashMap<Integer, Node> expressionHashToNode = new HashMap<>();

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
                        if (!(arg instanceof BoolExpr) && (RELEVANT_NODE.equals(arg.getFuncDecl().getDeclKind().name())) && i != 0) {

                            if (expressionHashToNode.get(e.hashCode()) == null){
                                Node node = createNode(e, order++);
                                expressionHashToNode.put(e.hashCode(), node);
                                if ("Goal".equals(node.getName())){
                                    this.setRoot(node);
                                }
                            }

                            Node node = createNode(arg, order++);
                            expressionHashToNode.put(arg.hashCode(), node);

                            this.addEdge(expressionHashToNode.get(e.hashCode()), expressionHashToNode.get(arg.hashCode()));
                        }
                    }

                }
            }
            expressions.add(newLevel);
            i++;
        }
    }

    private Node createNode(Expr e, int order){
        String name = extractTaskNameFromExpr(e);
        TaskType type = e.getNumArgs() > 2 ? TaskType.METHOD :  TaskType.ACTION;
        Node node = new Node();
        node.setName(name);
        node.setType(type);
        node.setN(order);
        return node;
    }


    public void addEdgeHelper(Node a, Node b) {
        LinkedList<Node> tmp = adjacencyMap.get(a);

        if (tmp != null) {
            tmp.remove(b);
        }
        else tmp = new LinkedList<>();
        tmp.add(b);
        adjacencyMap.put(a, tmp);
    }

    public void addEdge(Node source, Node destination) {

        // We make sure that every used node shows up in our .keySet()
        if (!adjacencyMap.keySet().contains(source))
            adjacencyMap.put(source, null);

        addEdgeHelper(source, destination);

        // If a graph is undirected, we want to add an edge from destination to source as well
        if (!directed) {
            addEdgeHelper(destination, source);
        }
    }

    public void printEdges() {
        for (Node node : adjacencyMap.keySet()) {
            System.out.print("The " + node.name + " has an edge towards: ");
            for (Node neighbor : adjacencyMap.get(node)) {
                System.out.print(neighbor.name + " ");
            }
            System.out.println();
        }
    }

    public boolean hasEdge(Node source, Node destination) {
        return adjacencyMap.containsKey(source) && adjacencyMap.get(source).contains(destination);
    }

    public void resetNodesVisited(){
        for(Node node : adjacencyMap.keySet()){
            node.unvisit();
        }
    }

    public void depthFirstSearch(Node node) {
        node.visit();
        System.out.print(node.name + " " + "\n");

        LinkedList<Node> allNeighbors = adjacencyMap.get(node);
        if (allNeighbors == null)
            return;

        for (Node neighbor : allNeighbors) {
            if (!neighbor.isVisited())
                depthFirstSearch(neighbor);
        }
    }

    public void dfsTyped(Node node, TaskType type, StringBuilder sb) {
        node.visit();
        if (type.equals(node.getType())) {
            String print = getStandardOutputOfTask(node);
            if (!"".equals(print)) {
                sb.append(print);
                sb.append(System.getProperty("line.separator"));
            }
        }

        LinkedList<Node> allNeighbors = adjacencyMap.get(node);
        if (allNeighbors == null)
            return;

        for (Node neighbor : allNeighbors) {
            if (!neighbor.isVisited())
                dfsTyped(neighbor, type, sb);
        }
    }

    private String getStandardOutputOfTask(Node node){
        StringBuilder sb = new StringBuilder();
        // we want to ignore our added actions
        if (node.getName().contains(METHOD_PRECONDITION_SUFFIX)){
            return "";
        }
        sb.append(node.getN());
        String label = node.getName().replace("(", "").replace(")", "").replace("|", "");

        try {
            String remove = label.substring(label.indexOf('#'), label.indexOf(' '));
            label = label.replace(remove, "");
        } catch (StringIndexOutOfBoundsException e){
            if ("Goal".equals(node.getName())){
                StringBuilder root = new StringBuilder();
                for (Node child : adjacencyMap.get(node)) {
                    root.append(" ").append(child.getN());
                }
                String goal = "root" + root.toString();
                return goal;
            }
            return "";
        }

        String[] args = label.split(" ");
        for (int i = 0; i < args.length; i++){
            try {
                int num = Integer.parseInt(args[i]);
                args[i] = intToObject.get(num);
            } catch (NumberFormatException e) {
                //nothing to there in such case
            }
            sb.append(" " + args[i]);
        }

        if (TaskType.METHOD == node.getType()){
            sb.append(" -> ");
            Node precondition = adjacencyMap.get(node).get(0);
            String preconditionName = precondition.getName();
            String methodName = preconditionName.substring(preconditionName.indexOf('|') + 1, preconditionName.indexOf(METHOD_PRECONDITION_SUFFIX));
            sb.append(methodName);
            for (Node child : adjacencyMap.get(node)){
                if (child.getName().contains(METHOD_PRECONDITION_SUFFIX)){
                    continue;
                }
                sb.append(" ");
                sb.append(child.getN());
            }
        }
        return sb.toString();
    }

    public String getStandardOutput(Node goal){
        StringBuilder sb = new StringBuilder();
        sb.append("==>").append(System.getProperty("line.separator"));
        this.dfsTyped(goal, TaskType.ACTION, sb);
        this.resetNodesVisited();
        this.dfsTyped(goal, TaskType.METHOD, sb);
        sb.append("<==").append(System.getProperty("line.separator"));
        return sb.toString();
    }

    public String getStandardOutput(){
        return getStandardOutput(root);
    }

    public void setIntToObjectFromInversemap(HashMap<String, Integer> objectToInt) {
        HashMap<Integer, String> map = new HashMap<>();
        for (Map.Entry<String, Integer> entry : objectToInt.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        this.intToObject = map;
    }

    public String getGraphInDotFormat() {
        StringBuilder sb = new StringBuilder();
        Expr root = answer.getArgs()[0];
        List<List<Expr>> expressions = new ArrayList<>();
        expressions.add(Arrays.asList(root));
        int i = 0;
        HashMap<Integer, Expr> exprHashMap = new HashMap<>();
        exprHashMap.put(root.hashCode(), root);

        sb.append("digraph " + problemName + " {").append(System.getProperty("line.separator"));

        while (expressions.get(i) != null && !expressions.get(i).isEmpty()) {
            List<Expr> newLevel = new ArrayList<>();
            for (Expr e : expressions.get(i)) {
                String label = getExpressionLabel(e);
                if (!(e instanceof BoolExpr) && i != 0){
                    String color;
                    if (RELEVANT_NODE.equals(e.getFuncDecl().getDeclKind().name())){
                        color = "red";
                        //if (!label.contains(METHOD_PRECONDITION_SUFFIX)) {
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
                        if (!(arg instanceof BoolExpr) && (RELEVANT_NODE.equals(arg.getFuncDecl().getDeclKind().name())) && i != 0) {
                            // && !label.contains(METHOD_PRECONDITION_SUFFIX) && !getExpressionLabel(arg).contains(METHOD_PRECONDITION_SUFFIX)) {
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
        return sb.toString();
    }

    private String getExpressionLabel(Expr e){
        String name = extractTaskNameFromExpr(e);
        String description = e.getNumArgs() > 2 ? "method" : "action";
        return e.hashCode() + " [label=\"" + name + "\"]" + "[description=\"" + description + "\"]";
    }

    private String extractTaskNameFromExpr(Expr e) {
        String name = "";
        if (!(e instanceof BoolExpr) && RELEVANT_NODE.equals(e.getFuncDecl().getDeclKind().name())){
            name =  (e.getArgs()[e.getNumArgs() - 1]).toString()
                    .replace("true", "").replace("false", "")
                    .replace("\n", "").replace("\r", "").trim().replaceAll(" +", " ");
        }
        return name;
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

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Expr getAnswer() {
        return answer;
    }

    public void setAnswer(Expr answer) {
        this.answer = answer;
    }
}