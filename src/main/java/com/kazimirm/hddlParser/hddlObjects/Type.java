package com.kazimirm.hddlParser.hddlObjects;

public class Type {
    private String name;
    private String baseType;

    public Type() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }
}
