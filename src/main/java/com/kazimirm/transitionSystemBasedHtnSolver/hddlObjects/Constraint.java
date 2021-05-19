package com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects;

public class Constraint {
    private Parameter param1;
    private Parameter param2;

    public Constraint() {
    }

    public Constraint(Parameter param1, Parameter param2) {
        this.param1 = param1;
        this.param2 = param2;
    }

    public Parameter getParam1() {
        return param1;
    }

    public void setParam1(Parameter param1) {
        this.param1 = param1;
    }

    public Parameter getParam2() {
        return param2;
    }

    public void setParam2(Parameter param2) {
        this.param2 = param2;
    }
}
