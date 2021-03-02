package com.kazimirm.hddlParser.hddlObjects;

import java.util.ArrayList;
import java.util.List;

public class Action {
    String Name;
    List<Parameter> parameters;
    List<Predicate> preconditions;
    List<Predicate> effects;

    //enriched in enricher
    List<List<Parameter>> parameterPermutations = new ArrayList<>();

    public Action() {
    }

    public Action(String name, List<Parameter> parameters, List<Predicate> preconditions, List<Predicate> effects) {
        Name = name;
        this.parameters = parameters;
        this.preconditions = preconditions;
        this.effects = effects;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<Predicate> getPreconditions() {
        return preconditions;
    }

    public void setPreconditions(List<Predicate> preconditions) {
        this.preconditions = preconditions;
    }

    public List<Predicate> getEffects() {
        return effects;
    }

    public void setEffects(List<Predicate> effects) {
        this.effects = effects;
    }

    public List<List<Parameter>> getParameterPermutations() {
        return parameterPermutations;
    }

    public void setParameterPermutations(List<List<Parameter>> parameterPermutations) {
        this.parameterPermutations = parameterPermutations;
    }
}
