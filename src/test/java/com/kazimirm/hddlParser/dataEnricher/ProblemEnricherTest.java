package com.kazimirm.hddlParser.dataEnricher;

import com.kazimirm.hddlParser.encoder.Z3Encoder;
import com.kazimirm.hddlParser.hddlObjects.Domain;
import com.kazimirm.hddlParser.hddlObjects.Problem;
import com.kazimirm.hddlParser.parser.ParseException;
import com.kazimirm.hddlParser.parser.ParserHDDL;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Test
    void encodeProblemPfile01() throws FileNotFoundException, ParseException {
        setUp(domain_htn, domain_htn_pfile01);
        ProblemEnricher pE = new ProblemEnricher(domain, problem);
        pE.enrichProblem();
        Z3Encoder encoder = new Z3Encoder(domain, problem);
        encoder.encodeToZ3Expressions();
    }

    @Test
    void testZ3() throws URISyntaxException, IOException {
        Context ctx = new Context();
        URL res = getClass().getClassLoader().getResource("test.smt2");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        String input = Files.readString(Path.of(absolutePath), StandardCharsets.UTF_8);
        BoolExpr[] expr = ctx.parseSMTLIB2String(input, null, null, null, null);

        Solver s = ctx.mkSolver();
        s.add(expr);
        System.out.println(s.toString());
        Status q = s.check();
        System.out.println("done");
    }

}