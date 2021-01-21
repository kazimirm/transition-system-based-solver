package com.kazimirm.hddlParser.hddlObjects;

import java.util.List;

public class Task {
    private String name;
    private List<Argument> parameters;

    public Task() {
    }

    public Task(String name, List<Argument> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Argument> getParameters() {
        return parameters;
    }

    public void setParameters(List<Argument> parameters) {
        this.parameters = parameters;
    }
}
