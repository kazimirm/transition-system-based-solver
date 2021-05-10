package com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects;

public class HtnInput {

    public InputType getType(){
       throw new IllegalArgumentException("The object is not Domain nor Problem type!");
    }
}

