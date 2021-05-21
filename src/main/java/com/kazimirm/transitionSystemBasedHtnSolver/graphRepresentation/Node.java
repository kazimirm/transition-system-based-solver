package com.kazimirm.transitionSystemBasedHtnSolver.graphRepresentation;

import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.TaskType;

public class Node {
    int n;
    String name;
    TaskType taskType;
    boolean visited;

    public Node() {
    }

    public Node(int n, String name) {
        this.n = n;
        this.name = name;
        visited = false;
    }

    // Two new methods we'll need in our traversal algorithms
    void visit() {
        visited = true;
    }

    void unvisit() {
        visited = false;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskType getType() {
        return taskType;
    }

    public void setType(TaskType taskType) {
        this.taskType = taskType;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
