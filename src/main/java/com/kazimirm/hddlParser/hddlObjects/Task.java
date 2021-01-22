package com.kazimirm.hddlParser.hddlObjects;

import java.util.List;

public class Task {
    private String name;
    private List<Parameter> parameters;

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
}
