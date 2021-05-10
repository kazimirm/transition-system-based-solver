package com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects;

import java.util.stream.Collectors;

public class Parameter extends Argument {
    public Parameter() {
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) {
            return false;
        }

        Parameter p = (Parameter) o;
        if (!this.getName().equals(p.getName())){
            return false;
        }

        return true;
    }
}
