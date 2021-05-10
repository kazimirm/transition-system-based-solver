package com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects;

public class Argument {
    private String name;
    private String type;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) {
            return false;
        }

        Argument a = (Argument) o;
        if (!this.getName().equals(a.getName())){
            return false;
        }

        return true;
    }
}