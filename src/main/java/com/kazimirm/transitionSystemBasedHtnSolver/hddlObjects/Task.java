package com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects;

import java.util.List;
import java.util.stream.Collectors;

public class Task {
    private String name;
    private List<Parameter> parameters;

    // set later in problem enrichment  - concrete predicates
    private List<Predicate> preConditions;
    private List<Predicate> postConditions;

    public Task() {
    }

    public Task(String name, List<Parameter> parameters) {
        this.name = name;
        this.parameters = parameters;
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

    public List<Predicate> getPreConditions() {
        return preConditions;
    }

    public void setPreConditions(List<Predicate> preConditions) {
        this.preConditions = preConditions;
    }

    public List<Predicate> getPostConditions() {
        return postConditions;
    }

    public void setPostConditions(List<Predicate> postConditions) {
        this.postConditions = postConditions;
    }

    @Override
    public String toString() {
        return getName() + "(" + getParameters().stream().map(Object::toString).collect(Collectors.joining(", ")) + ", " +
                getPreConditions().stream().map(Object::toString).collect(Collectors.joining(", ")) + ", " +
                getPostConditions().stream().map(Object::toString).collect(Collectors.joining(", ")) + ")";
    }
}
