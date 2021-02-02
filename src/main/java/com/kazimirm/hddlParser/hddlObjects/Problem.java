package com.kazimirm.hddlParser.hddlObjects;

import java.util.List;

public class Problem {
    private String name;
    private Domain domain;
    private List<Argument> objects;
    //private <?> htn;
    private List<Predicate> init;

    public Problem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public List<Argument> getObjects() {
        return objects;
    }

    public void setObjects(List<Argument> objects) {
        this.objects = objects;
    }

    public List<Predicate> getInit() {
        return init;
    }

    public void setInit(List<Predicate> init) {
        this.init = init;
    }
}
