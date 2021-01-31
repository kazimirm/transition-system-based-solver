package com.kazimirm.hddlParser.hddlObjects;

import java.util.List;

public class Predicate {
    private String name;
    private Boolean value;
    private List<Argument> arguments;

    public Predicate() {
    }

    public Predicate(String name, Boolean value, List<Argument> arguments) {
        this.name = name;
        this.value = value;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }
}


