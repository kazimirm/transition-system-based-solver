package com.kazimirm.hddlParser.dataEnricher;

import com.kazimirm.hddlParser.hddlObjects.*;

import java.util.*;

public class ProblemEnricher {
    private Domain domain;
    private Problem problem;
    private List<Predicate> predicates = new ArrayList<>();;
    private List<String> result = new ArrayList<>();

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
        HashMap<String, List<Argument>> objectsToTypedListsMap = new HashMap<>();
        HashMap<String, Type> nameToTypeMap = new HashMap<>();


        for (Type t: types) {
            objectsToTypedListsMap.put(t.getName(), new ArrayList<Argument>());
            nameToTypeMap.put(t.getName(), t);
        }

        for (Argument a: problem.getObjects()){

            String type = a.getType();
            objectsToTypedListsMap.get(type).add(a);
            String baseType = nameToTypeMap.get(type).getBaseType();

            if (objectsToTypedListsMap.containsKey(baseType)){
                objectsToTypedListsMap.get(baseType).add(a);
            }
        }



        for (Predicate p: domain.getPredicates()) {
            List<List<Argument>> lists = new ArrayList<>();
            for (Argument a: p.getArguments()) {
                lists.add(objectsToTypedListsMap.get(a.getType()));
            }
            generatePermutationsForPredicate(p, lists);
        }
    }

    /**
     * This method is used to enhance all predicates. We calculate all permutations of given predicate and check
     * predicates from init block of problem (problem.getInit() which returns all predicates from problem -
     * all these predicates are true.) If the predicate is not there -> is false.
     *
     * @param p - predicate
     * @param lists - list of List<Argument>. The size of list is equal to number of arguments to predicate.
     */
    private void generatePermutationsForPredicate(Predicate p, List<List<Argument>> lists) {
        generatePermutationsForPredicate(p, lists, predicates, 0, "");
    }



    /**
     *
     * @param p - predicate
     * @param lists - list of List<Argument>. The size of list is equal to number of arguments to predicate.
     * @param result - the final List where all predicates are stored (All permutations with correct values).
     * @param depth - field for recursion
     * @param current - field recursion
     */
    private void generatePermutationsForPredicate(Predicate p, List<List<Argument>> lists, List<Predicate> result, int depth, String current) {
        if (depth == lists.size()) {
            Predicate predicate = new Predicate();
            predicate.setName(p.getName());
            List<String> argsNames = Arrays.asList(current.split(";"));
            List<Argument> args = new ArrayList<>();
            for (int i = 0; i < p.getArguments().size(); i++){
                Argument a = new Argument();
                a.setName(argsNames.get(i));
                a.setType(p.getArguments().get(i).getType());
                args.add(a);
            }
            predicate.setArguments(args);

            if (problem.getInit().contains(predicate)){
                predicate.setValue(true);
            } else {
                predicate.setValue(false);
            }

            predicates.add(predicate);
            return;
        }

        for (int i = 0; i < lists.get(depth).size(); i++) {
            generatePermutationsForPredicate(p, lists, predicates, depth + 1, current + lists.get(depth).get(i).getName() + ";");
        }
    }
}
