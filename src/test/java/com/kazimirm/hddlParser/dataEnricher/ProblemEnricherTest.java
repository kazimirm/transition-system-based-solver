package com.kazimirm.hddlParser.dataEnricher;

import com.kazimirm.hddlParser.hddlObjects.Domain;
import com.kazimirm.hddlParser.hddlObjects.Problem;
import com.kazimirm.hddlParser.parser.ParseException;
import com.kazimirm.hddlParser.parser.ParserHDDL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ProblemEnricherTest {
    private final String DOMAIN_BASIC_FROM_PRELIMINARIES = "domain_basic_from_preliminaries.txt";
    private final String PROBLEM_BASIC_FROM_PRELIMINARIES = "problem_basic_from_preliminaries.txt";
    private final String domain_htn = "domain-htn.hddl";
    private final String domain_htn_pfile01 = "pfile01.hddl";
    private Domain domain;
    private Problem problem;

    void setUp(String d, String p) throws FileNotFoundException, ParseException {
        ParserHDDL parser = new ParserHDDL(InputStream.nullInputStream());
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
        pE.enrichProblem();
    }

    @Test
    void enrichProblemPfile01() throws FileNotFoundException, ParseException {
        setUp(domain_htn, domain_htn_pfile01);
        ProblemEnricher pE = new ProblemEnricher(domain, problem);
        pE.enrichProblem();
    }
}