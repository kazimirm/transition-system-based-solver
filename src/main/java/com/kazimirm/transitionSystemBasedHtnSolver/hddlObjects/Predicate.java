package com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Predicate implements Cloneable{
    private String name;
    private int index;
    private Boolean value;
    private List<Argument> arguments;

    public Predicate() {
    }

    public Predicate(String name, Boolean value, List<Argument> arguments) {
        this.name = name;
        this.value = value;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public String getNameWithOptionalNegation() {
        return (value) ? name : "¬" + name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Predicate p = (Predicate) o;
        if (!this.getName().equals(p.getName())){
            return false;
        }

        for (Argument a: this.getArguments()){
            if (!a.getName().equals(p.getArguments().get(arguments.indexOf(a)).getName())){
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format(getName() +"(" + arguments.stream().map(Argument::getName)
                .collect(Collectors.joining(", ")) + ")" + "[" + index + "]");
    }

    public String toStringWithOptionalNegation() {
        return String.format(getNameWithOptionalNegation() +"(" + arguments.stream().map(Argument::getName)
                .collect(Collectors.joining(", ")) + ")" + "[" + index + "]");
    }


    public String toStringWithoutIndex() {
        return String.format(getName() +"(" + arguments.stream().map(Argument::getName)
                .collect(Collectors.joining(", ")) + ")");
    }


    @Override
    public Predicate clone() throws CloneNotSupportedException {
        Predicate clone = new Predicate();
        clone.setName(this.name);
        clone.setIndex(this.index);
        clone.setValue(this.value);
        List<Argument> args = new ArrayList<>();
        for (Argument argument: this.arguments){
            Argument a = new Argument();
            a.setName(argument.getName());
            a.setType(argument.getType());
            args.add(a);
        }
        clone.setArguments(args);

        return clone;
    }

}


