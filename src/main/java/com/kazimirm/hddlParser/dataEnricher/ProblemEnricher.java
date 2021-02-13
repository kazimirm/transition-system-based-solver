package com.kazimirm.hddlParser.dataEnricher;

import com.kazimirm.hddlParser.hddlObjects.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class ProblemEnricher {
    private Domain domain;
    private Problem problem;
    private List<Predicate> predicates;

    public ProblemEnricher(Domain domain, Problem problem) {
        this.domain = domain;
        this.problem = problem;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public Problem enrichProblem(){
        enrichPredicates();
        return problem;
    }

    private void enrichPredicates(){
        List<Type> types = domain.getTypes();
        HashMap<String, List<Argument>> objectsTypesList = new HashMap<>();
        for (Type t: types) {
            objectsTypesList.put(t.getName(), new ArrayList<Argument>());
        }

        for (Argument a: problem.getObjects()){
            String type = a.getType();
            objectsTypesList.get(type).add(a);
        }

//        List<Predicate> predicates = domain.getPredicates();

//        for (Predicate p: domain.getPredicates()){
//            List<Argument> args = p.getArguments();
//        }









        System.out.println("hihi");
    }




}
