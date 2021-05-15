package com.kazimirm.transitionSystemBasedHtnSolver.ipcTasks;

import com.kazimirm.transitionSystemBasedHtnSolver.dataEnricher.ProblemEnricher;
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
    void testRoverTransport() throws FileNotFoundException, ParseException {
        dmn = "IPC_2020/p-rover01/rover/domains/rover-domain.hddl";
        prb = "IPC_2020/p-rover01/rover/problems/pfile01.hddl";
        setUp(dmn, prb);
        ProblemEnricher pE = new ProblemEnricher(domain, problem);
        System.out.println();
    }
}
