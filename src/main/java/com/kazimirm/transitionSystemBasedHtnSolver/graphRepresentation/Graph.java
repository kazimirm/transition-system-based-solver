package com.kazimirm.transitionSystemBasedHtnSolver.graphRepresentation;

import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.TaskType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Graph {

    // Each node maps to a list of all his neighbors
    private HashMap<Node, LinkedList<Node>> adjacencyMap;
    private boolean directed;
    private HashMap<Integer, String> intToObject;
    private Node root;

    public Graph(boolean directed) {
        this.directed = directed;
        adjacencyMap = new HashMap<>();
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
        if (node.getName().contains("_Precondition")){
            return "";
        }
        sb.append(node.getN() + " ");
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
            sb.append(args[i] + " ");
        }

        if (TaskType.METHOD == node.getType()){
            sb.append(" -> ");
            Node precondition = adjacencyMap.get(node).get(0);
            String preconditionName = precondition.getName();
            String methodName = preconditionName.substring(0, preconditionName.indexOf('#'))
                    .replace("(", "").replace(")", "").replace("|", "");
            sb.append(methodName);
            for (Node child : adjacencyMap.get(node)){
                if (child.getName().contains("_Precondition#")){
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

    public void printStandardOutput(){
        System.out.println(getStandardOutput(root));
    }

    public void setIntFromInversemap(LinkedHashMap<String, Integer> objectToInt) {
        HashMap<Integer, String> map = new HashMap<>();
        for (Map.Entry<String, Integer> entry : objectToInt.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        this.intToObject = map;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}