package com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects;

import java.util.List;

public class Method {
    private String name;
    private List<Parameter> parameters;
    private Task task;
    private List<Predicate> preconditions;
    private List<Subtask> subtasks;
    private List<Ordering> ordering;
    private List<Constraint> constraints;

    public Method() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<Predicate> getPreconditions() {
        return preconditions;
    }

    public void setPreconditions(List<Predicate> preconditions) {
        this.preconditions = preconditions;
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

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }
}
