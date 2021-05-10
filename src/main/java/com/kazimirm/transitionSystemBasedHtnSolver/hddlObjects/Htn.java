package com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects;

import java.util.List;

public class Htn {
    List<Parameter> parameters;
    List<Subtask> subtasks;
    List<Ordering> ordering;

    public Htn() {
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public List<Ordering> getOrdering() {
        return ordering;
    }

    public void setOrdering(List<Ordering> ordering) {
        this.ordering = ordering;
    }
}
