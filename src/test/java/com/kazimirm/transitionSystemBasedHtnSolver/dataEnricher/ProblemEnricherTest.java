package com.kazimirm.transitionSystemBasedHtnSolver.dataEnricher;

import com.kazimirm.transitionSystemBasedHtnSolver.encoder.Z3Encoder;
import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.Domain;
import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.Problem;
import com.kazimirm.transitionSystemBasedHtnSolver.parser.ParseException;
import com.kazimirm.transitionSystemBasedHtnSolver.parser.ParserHDDL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Objects;

class ProblemEnricherTest {
    private final String DOMAIN_BASIC_FROM_PRELIMINARIES = "domain_basic_from_preliminaries.txt";
    private final String PROBLEM_BASIC_FROM_PRELIMINARIES = "problem_basic_from_preliminaries.txt";
    private final String domain_htn = "domain-htn.hddl";
    private final String domain_htn_pfile01 = "pfile01.hddl";
    private final String domain_htn_pfile02 = "pfile02.hddl";
    private final String dmn = "rover-domain.hddl";
    private final String prb = "rover-pfile01.hddl";
    private Domain domain;
    private Problem problem;
    private ParserHDDL parser;

    @BeforeAll
    static void setup(){
        ParserHDDL parser = new ParserHDDL(InputStream.nullInputStream());
    }

    void setUp(String d, String p) throws FileNotFoundException, ParseException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(d).getFile());
        InputStream targetStream = new FileInputStream(file);

        parser.ReInit(targetStream);
        domain = parser.parseDomain();


        file = new File(Objects.requireNonNull(classLoader.getResource(p)).getFile());
        targetStream = new FileInputStream(file);

        parser.ReInit(targetStream);
        problem = parser.parseProblem();
    }

    @Test
    void enrichProblemPreliminaries() throws FileNotFoundException, ParseException {
        setUp(DOMAIN_BASIC_FROM_PRELIMINARIES, PROBLEM_BASIC_FROM_PRELIMINARIES);
        ProblemEnricher pE = new ProblemEnricher(domain, problem);
    }

    @Test
    void enrichProblemPfile01() throws FileNotFoundException, ParseException {
        setUp(domain_htn, domain_htn_pfile01);
        ProblemEnricher pE = new ProblemEnricher(domain, problem);
    }

    @Test
    void encodeProblemPfile01() throws FileNotFoundException, ParseException {
        setUp(domain_htn, domain_htn_pfile01);
        ProblemEnricher pE = new ProblemEnricher(domain, problem);
        Z3Encoder encoder = new Z3Encoder(pE.getDomain(), pE.getProblem());
        encoder.encodeToZ3ExpressionsAndGetResult();
    }

    @Test
    void encodeProblemPfile02() throws FileNotFoundException, ParseException {
        setUp(domain_htn, domain_htn_pfile02);
        ProblemEnricher pE = new ProblemEnricher(domain, problem);
        Z3Encoder encoder = new Z3Encoder(pE.getDomain(), pE.getProblem());
        encoder.encodeToZ3ExpressionsAndGetResult();
    }

    @Test
    void encodeProblemPreliminaries() throws FileNotFoundException, ParseException {
        setUp(DOMAIN_BASIC_FROM_PRELIMINARIES, PROBLEM_BASIC_FROM_PRELIMINARIES);
        ProblemEnricher pE = new ProblemEnricher(domain, problem);
        Z3Encoder encoder = new Z3Encoder(pE.getDomain(), pE.getProblem());
        encoder.encodeToZ3ExpressionsAndGetResult();
    }

    @Test
    void encodeProblemRover() throws FileNotFoundException, ParseException {
        setUp(dmn, prb);
        ProblemEnricher pE = new ProblemEnricher(domain, problem);
        Z3Encoder encoder = new Z3Encoder(domain, problem);
        encoder.encodeToZ3ExpressionsAndGetResult();
    }
}