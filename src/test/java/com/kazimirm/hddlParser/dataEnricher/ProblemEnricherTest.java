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
    private Domain domain;
    private Problem problem;

    @BeforeEach
    void setUp() throws FileNotFoundException, ParseException {
        ParserHDDL parser = new ParserHDDL(InputStream.nullInputStream());
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(DOMAIN_BASIC_FROM_PRELIMINARIES).getFile());
        InputStream targetStream = new FileInputStream(file);

        parser.ReInit(targetStream);
        domain = parser.parseDomain();


        file = new File(Objects.requireNonNull(classLoader.getResource(PROBLEM_BASIC_FROM_PRELIMINARIES)).getFile());
        targetStream = new FileInputStream(file);

        parser.ReInit(targetStream);
        problem = parser.parseProblem();
    }

    @Test
    void enrichProblem() {
        ProblemEnricher pE = new ProblemEnricher(domain, problem);
        pE.enrichProblem();
    }
}