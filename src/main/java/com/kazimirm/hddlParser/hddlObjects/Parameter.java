package com.kazimirm.hddlParser.hddlObjects;

import java.util.stream.Collectors;

public class Parameter extends Argument {
    public Parameter() {
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
