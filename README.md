# Transition-system-based HTN planning solver

This project contains a new-approach solver of HTN tasks in totally-order HDDL language used as bachelor thesis for IPSS on MFF CUNI 2021.

## Abstract
Constrained Horn Clauses (CHC) has recently emerged as an intermediate language for expressing verification conditions in software verification. They extend the classical formats for SAT and SMT by capturing high-level constructs such as loops and recursion in low-level logical representation.
The state-of-the-art dedicated CHC solvers employ powerful algorithms to detect reachability of error states in the system. The format of CHC is powerful enough to capture the hierarchical structure of HP problems, and the reachability algorithms in CHC solvers can be used to find corresponding plans.

The aim of this project is to investigate possible compilation techniques of HP to CHC, i.e., propose an encoding of totally-ordered HP problems into CHC, use off-the-shelf CHC solver to solve the compiled problem and extract the plan from the CHC solution.
The compilation technique will be implemented and its performance evaluated on a suitable subset of benchmarks from International Planning Competition 2020.

## How to run
The program has currently the form of console application (executable jar). It takes these parameters split by space:
> 1. path to file of the HTN domain in HDDL format
> 2. path to the file of HTN problem in HDDL format relevant to the domain from previous parameter
> 3. Optionally, if third parameter "-dot" is present, in addition to the standard output, also output of graph in a DOT format is printed

In order to be able to run the jar, correct version of java must be installed. The whole program uses Open-JDK 15.0.1.

For example:
"java -jar Transition-system-based_HTN_planning_solver-1.0-SNAPSHOT.jar rover-domain.hddl file01.hddl -dot"