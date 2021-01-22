package com.kazimirm.hddlParser.hddlObjects;

import java.util.List;

public class Task {
    private String name;
    private List<Argument> arguments;

    public Task() {
    }

    public Task(String name, List<Argument> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }
}
