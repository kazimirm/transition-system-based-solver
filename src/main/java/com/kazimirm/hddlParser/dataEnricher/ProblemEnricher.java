package com.kazimirm.hddlParser.dataEnricher;

import com.kazimirm.hddlParser.hddlObjects.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;
import java.util.stream.Collectors;

public class ProblemEnricher {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private Domain domain;
    private Problem problem;
    private HashMap<String, List<Argument>> objectsToTypedListsMap = new HashMap<>();
    private List<Predicate> predicates = new ArrayList<>();;
    private HashMap<String, Type> nameToTypeMap = new HashMap<>();

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
        enrichAbstractTasks();
        return problem;
    }

    /**
     *  This method takes given domain and problem nad creates variable for all ground instances of predicate
     */
    private void enrichPredicates(){

        for (Type t: domain.getTypes()) {
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
        logger.debug("PREDICATES: ");
        logger.debug(predicates.stream().map(Predicate::toStringWithoutIndex)
                .collect(Collectors.joining(", ")));

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
     * @param current - field for recursion
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

    private void enrichAbstractTasks() {
        // New predicates variables preparation
        logger.debug("ABSTRACT TASKS:");
        List<Predicate> predicatesVariables = new ArrayList<>(predicates);
        for (Predicate p: predicatesVariables){
            p.setValue(null);
        }

        for (Method m: domain.getMethods()){
            Task t = m.getTask();
            List<Predicate> preConditions = cloneList(predicatesVariables);
            preConditions.forEach(p -> p.setIndex(0));
            List<Predicate> postConditions = cloneList(predicatesVariables);
            postConditions.forEach(p -> p.setIndex(m.getSubtasks().size()));
            t.setPreConditions(preConditions);
            t.setPostConditions(postConditions);
            String task = t.toString();
            List <String> subtasks = new ArrayList<>();

            for (Subtask s: m.getSubtasks()){
                Task st = s.getTask();
                List<Predicate> subTaskPreConditions = cloneList(predicatesVariables);
                subTaskPreConditions.forEach(p -> p.setIndex(m.getSubtasks().indexOf(s)));
                List<Predicate> subTaskPostConditions = cloneList(predicatesVariables);
                subTaskPostConditions.forEach(p -> p.setIndex(m.getSubtasks().indexOf(s) + 1));
                st.setPreConditions(subTaskPreConditions);
                st.setPostConditions(subTaskPostConditions);
                subtasks.add(st.toString());
            }

            String abstractTask = (task + ":- " + System.getProperty("line.separator") + subtasks.stream().map(Object::toString).
                    collect(Collectors.joining(" & " + System.getProperty("line.separator"))));
            logger.debug(abstractTask);
        }
    }

    private void enrichPrimitiveTasks(){
        for (Action action: domain.getActions()){

        }
    }

    public List<Predicate> cloneList(List<Predicate> list) {
        try {
            List<Predicate> clone = new ArrayList<Predicate>(list.size());
            for (Predicate item : list) clone.add(item.clone());
            return clone;
        }
        catch (CloneNotSupportedException e){
            logger.error("List {} cannot be cloned", list);
            return null;
        }
    }
}
