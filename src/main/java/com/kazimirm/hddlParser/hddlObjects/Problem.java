package com.kazimirm.hddlParser.hddlObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Problem extends HtnInput{
    private String name;
    private Domain domain;
    private List<Argument> objects;
    private Htn htn;
    private List<Predicate> init;

    // filled in enricher and used by encoder
    private HashMap<String, List<Argument>> objectsToTypedLists = new HashMap<>(); // for each type creates list with such objects
    private HashMap<String, Type> typeNameToType = new HashMap<>();
    private HashMap<String, Integer> objectToInt = new HashMap<>();
    private List<Predicate> predicates = new ArrayList<>();

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

    public Htn getHtn() {
        return htn;
    }

    public void setHtn(Htn htn) {
        this.htn = htn;
    }

    public List<Predicate> getInit() {
        return init;
    }

    public void setInit(List<Predicate> init) {
        this.init = init;
    }

    @Override
    public InputType getType() {
        return InputType.PROBLEM;
    }

    public HashMap<String, List<Argument>> getObjectsToTypedLists() {
        return objectsToTypedLists;
    }

    public void setObjectsToTypedLists(HashMap<String, List<Argument>> objectsToTypedLists) {
        this.objectsToTypedLists = objectsToTypedLists;
    }

    public HashMap<String, Type> getTypeNameToType() {
        return typeNameToType;
    }

    public void setTypeNameToType(HashMap<String, Type> typeNameToType) {
        this.typeNameToType = typeNameToType;
    }

    public HashMap<String, Integer> getObjectToInt() {
        return objectToInt;
    }

    public void setObjectToInt(HashMap<String, Integer> objectToInt) {
        this.objectToInt = objectToInt;
    }

    public List<Predicate> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<Predicate> predicates) {
        this.predicates = predicates;
    }
}
