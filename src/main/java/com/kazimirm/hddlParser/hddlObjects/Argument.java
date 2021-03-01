package com.kazimirm.hddlParser.hddlObjects;

public class Argument {
    private String name;
    private String type;
    private int value;
    private int id; // Z3 does not like String, we'll set unique int id according to order in declaration

    public Argument() {
    }

    public Argument(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}