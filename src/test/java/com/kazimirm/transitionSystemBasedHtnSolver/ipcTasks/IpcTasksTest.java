package com.kazimirm.transitionSystemBasedHtnSolver.ipcTasks;

import com.kazimirm.transitionSystemBasedHtnSolver.answerExtractor.AnswerExtractor;
import com.kazimirm.transitionSystemBasedHtnSolver.answerExtractor.graphRepresentation.Graph;
import com.kazimirm.transitionSystemBasedHtnSolver.dataEnricher.ProblemEnricher;
import com.kazimirm.transitionSystemBasedHtnSolver.encoder.Z3Encoder;
import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.Domain;
import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.Problem;
import com.kazimirm.transitionSystemBasedHtnSolver.parser.ParseException;
import com.kazimirm.transitionSystemBasedHtnSolver.parser.ParserHDDL;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class IpcTasksTest {
    private String dmn;
    private String prb;
    private Domain domain;
    private Problem problem;
    private ParserHDDL parser;

    void setUp(String d, String p) throws FileNotFoundException, ParseException {
        ClassLoader classLoader = this.getClass().getClassLoader();

        File file = new File(classLoader.getResource(d).getFile());
        InputStream targetStream = new FileInputStream(file);

        parser = new ParserHDDL(targetStream);
        domain = parser.parseDomain();

        file = new File(Objects.requireNonNull(classLoader.getResource(p)).getFile());
        targetStream = new FileInputStream(file);

        parser = new ParserHDDL(targetStream);
        problem = parser.parseProblem();
    }

    @Test
    void testPRover01() throws FileNotFoundException, ParseException {
        dmn = "IPC_2020/p-rover01/rover/domains/rover-domain.hddl";
        prb = "IPC_2020/p-rover01/rover/problems/pfile01.hddl";
        setUp(dmn, prb);
        ProblemEnricher pE = new ProblemEnricher(domain, problem);
        Z3Encoder encoder = new Z3Encoder(pE.getDomain(), pE.getProblem());
        encoder.encodeToZ3ExpressionsAndGetResult();
        AnswerExtractor extractor = new AnswerExtractor(encoder);
        Graph graph = extractor.getGraphFromAnswer();
        System.out.println(graph.getStandardOutput());
    }

    @Test
    void testPTransport01() throws FileNotFoundException, ParseException {
        dmn = "IPC_2020/p-transport01/transport/domains/domain-htn.hddl";
        prb = "IPC_2020/p-transport01/transport/problems/pfile01.hddl";
        setUp(dmn, prb);
        ProblemEnricher pE = new ProblemEnricher(domain, problem);
        Z3Encoder encoder = new Z3Encoder(pE.getDomain(), pE.getProblem());
        encoder.encodeToZ3ExpressionsAndGetResult();
        AnswerExtractor extractor = new AnswerExtractor(encoder);
        Graph graph = extractor.getGraphFromAnswer();
        System.out.println(graph.getStandardOutput());
    }

    @Test
    // Uses constraints
    void testSattelite01() throws FileNotFoundException, ParseException {
        dmn = "IPC_2020/satellite01/Satellite/domains/satellite2.hddl";
        prb = "IPC_2020/satellite01/Satellite/problems/1obs-1sat-1mod.hddl";
        setUp(dmn, prb);
        ProblemEnricher pE = new ProblemEnricher(domain, problem);
        Z3Encoder encoder = new Z3Encoder(pE.getDomain(), pE.getProblem());
        encoder.encodeToZ3ExpressionsAndGetResult();
        System.out.println();
    }

//    @Test
//    // Uses constraints
//    void testUmTranslog() throws FileNotFoundException, ParseException {
//        dmn = "IPC_2020/um-translog01/UM-Translog/domains/UMTranslog.hddl";
//        prb = "IPC_2020/um-translog01/UM-Translog/problems/01-A-AirplanesHub.hddl";
//        setUp(dmn, prb);
//        ProblemEnricher pE = new ProblemEnricher(domain, problem);
//        Z3Encoder encoder = new Z3Encoder(pE.getDomain(), pE.getProblem());
//        encoder.encodeToZ3ExpressionsAndGetResult();
//        System.out.println();
//    }
}
