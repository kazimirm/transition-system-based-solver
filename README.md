# Transition-system-based HTN planning solver

Repository of HTN solver based on translating the planning problem to a reachability problem in a transition system and using off-the-shelf reachability solver for the actual solving.
Expected input are HTN tasks in totally-ordered HDDL language. This project was developed as bachelor thesis for IPSS on MFF CUNI 2021.

## Abstract
Constrained Horn Clauses (CHC) has recently emerged as an intermediate language for expressing verification conditions in software verification. They extend the classical formats for SAT and SMT by capturing high-level constructs such as loops and recursion in low-level logical representation.
The state-of-the-art dedicated CHC solvers employ powerful algorithms to detect reachability of error states in the system. The format of CHC is powerful enough to capture the hierarchical structure of HP problems, and the reachability algorithms in CHC solvers can be used to find corresponding plans.

The aim of this project is to investigate possible compilation techniques of HP to CHC, i.e., propose an encoding of totally-ordered HP problems into CHC, use off-the-shelf CHC solver to solve the compiled problem and extract the plan from the CHC solution.
The compilation technique will be implemented and its performance evaluated on a suitable subset of benchmarks from International Planning Competition 2020.

## Prerequisities

The usage of solver is pretty simple without numerous demands. It comes in a form of executable jar so it runs using commands `'java jar jarname'` . 
The only precondition we expect is that user has installed:

Java JDK 15 (https://jdk.java.net/archive/)

Maven (https://maven.apache.org/download.cgi)

Z3 theorem prover (https://github.com/Z3Prover/z3/releases)

Also make sure `'JAVA_HOME'` variable is correctly set as well as other environment paths on used machine (especially Java, Maven and Z3). 
Presented build steps were tested on the Windows platform but the process is analogical on Unix as well.

These dependencies can be easily checked. For Java, open command prompt
and type `'java -version'`. The response should be similar to example
below (in our case we used OpenJDK).

    C:\Users\testing>java -version
    openjdk version "15.0.1" 2020-10-20
    OpenJDK Runtime Environment (build 15.0.1+9-18)
    OpenJDK 64-Bit Server VM (build 15.0.1+9-18, mixed mode, sharing)

Maven installation can be checked by command `'mvn -version'`. Again,
the response should look like in our example:

    C:\Users\testing>mvn -version
    Apache Maven 3.8.1 (05c21c65bdfed0f71a2f2ada8b84da59348c4c5d)
    Maven home: C:\Program Files (x86)\Maven\apache-maven-3.8.1-bin\apache-maven-3.8.1\bin\..
    Java version: 15.0.1, vendor: Oracle Corporation, runtime: C:\Users\cekvi\.jdks\openjdk-15.0.1
    Default locale: en_US, platform encoding: Cp1250
    OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"

Z3 theorem prover installation can be checked by command
`'Z3 -version'`. The response should look like this:

    C:\Users\testing> Z3 -version
    Z3 version 4.8.10 - 64 bit

## How to build

Firstly, project repository should be downloaded. Once the files are
unzipped at a desired location, open the command prompt and execute
these commands:

    cd $PROJECT_ROOT_DIRECTORY
    mvn clean install

## How to run
The program has currently the form of console application (executable jar). It takes these parameters split by space:
> 1. path to file of the HTN domain in HDDL format
> 2. path to the file of HTN problem in HDDL format relevant to the domain from previous parameter
> 3. Optionally, if third parameter "-dot" is present, in addition to the standard output, also output of graph in a DOT format is printed

For example, from the location in `'target'` directory, we can execute this command:

    java -jar Transition-system-based_HTN_planning_solver-1.0-SNAPSHOT.jar ..\src\test\resources\IPC_2020\rover01\rover\domains\rover-domain.hddl ..\src\test\resources\IPC_2020\rover01\rover\problems\pfile01.hddl