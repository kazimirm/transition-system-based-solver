package com.kazimirm.hddlParser.hddlObjects;

public class Requirement {
    private String value;

    public Requirement(String value) {
        this.value = value;
    }

    public Requirement() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
