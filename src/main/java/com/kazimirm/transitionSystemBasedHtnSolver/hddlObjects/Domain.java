package com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects;

import java.util.List;

public class Domain extends HtnInput{
    private String name;
    private List<Requirement> requirements;
    private List<Type> types;
    private List<Constant>  constants;
    private List<Predicate> predicates;
    private List<Task> tasks;
    private List<Method> methods;
    private List<Action> actions;

    public Domain(String name, List<Requirement> requirements, List<Type> types, List<Constant> constants, List<Predicate> predicates, List<Task> tasks, List<Method> methods, List<Action> actions) {
        this.name = name;
        this.requirements = requirements;
        this.types = types;
        this.constants = constants;
        this.predicates = predicates;
        this.tasks = tasks;
        this.methods = methods;
        this.actions = actions;
    }

    public Domain() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public List<Constant> getConstants() {
        return constants;
    }

    public void setConstants(List<Constant> constants) {
        this.constants = constants;
    }

    public List<Predicate> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<Predicate> predicates) {
        this.predicates = predicates;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    @Override
    public InputType getType() {
        return InputType.DOMAIN;
    }
}
