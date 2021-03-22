package com.kazimirm.hddlParser.hddlObjects;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Action {
    String Name;
    List<Parameter> parameters;
    List<Predicate> preconditions;
    List<Predicate> effects;

    //enriched in enricher
    List<HashMap<String, Parameter>> parameterPermutations = new ArrayList<>();

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

    public List<HashMap<String, Parameter>> getParameterPermutations() {
        return parameterPermutations;
    }

    public void setParameterPermutations(List<HashMap<String, Parameter>> parameterPermutations) {
        this.parameterPermutations = parameterPermutations;
    }

    public List<Predicate> getConcretePredicates(List<Predicate> list, List<Parameter> permutation){
        HashMap<String, String> parameterToObjectName = new HashMap<>();

        for (Parameter p: this.parameters){
            parameterToObjectName.put(p.getName(), permutation.get(this.parameters.indexOf(p)).getName());
        }

        List<Predicate> predicates = new ArrayList<>();
        for (Predicate predicate: list){
            Predicate p = new Predicate();
            p.setName(predicate.getName());
            List<Argument> args = new ArrayList<>();
            for (Argument a: predicate.getArguments()){
                Argument arg = new Argument();
                arg.setName(parameterToObjectName.get(a.getName()));
                args.add(arg);
            }
            p.setValue(predicate.getValue());
            p.setIndex(predicate.getIndex());
            p.setArguments(args);
            predicates.add(p);
        }
        return predicates;
    }
}
