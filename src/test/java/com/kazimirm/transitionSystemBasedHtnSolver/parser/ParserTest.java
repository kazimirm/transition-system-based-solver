package com.kazimirm.transitionSystemBasedHtnSolver.parser;

import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.Domain;
import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.Problem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.*;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParserTest {

    private final String DOMAIN_BASIC_FROM_PRELIMINARIES = "domain_basic_from_preliminaries.txt";
    private final String PROBLEM_BASIC_FROM_PRELIMINARIES = "problem_basic_from_preliminaries.txt";
    private final String DOMAIN_INPUT_INVALID_1 = "domain_input_invalid_2.txt"; // unexpected text after requirements
    private final String DOMAIN_INPUT_INVALID_2 = "domain_input_invalid_1.txt"; // wrong space
    private final String PROBLEM_INPUT_BASIC_1 = "problem_input_basic_1.txt";
    private final String DOMAIN_INPUT_BASIC_1 = "domain_input_basic_1.txt";
    private ParserHDDL parser;
    private Domain domain;
    private Problem problem;

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

    /**
     *  Currently, these are just dummy tests and served mostly for debugging purposes
     */

    @Test
    public void testParser_domain_basicInput_from_preliminaries_valid() throws FileNotFoundException, ParseException {
        setUp(DOMAIN_BASIC_FROM_PRELIMINARIES, PROBLEM_INPUT_BASIC_1);
        assertEquals("domain_htn", domain.getName());
    }

    @Test
    public void testParser_basicInput_valid() throws FileNotFoundException, ParseException {
        setUp(DOMAIN_INPUT_BASIC_1, PROBLEM_INPUT_BASIC_1);
        assertEquals("domain_htn", domain.getName());
        assertEquals(3, domain.getRequirements().size());
        assertEquals(6, domain.getTypes().size());
        assertEquals(5, domain.getPredicates().size());
        assertEquals(4, domain.getTasks().size());
    }

    @Test
    public void testParser_basicInput_invalid_1() throws FileNotFoundException, ParseException {
        assertThrows(ParseException.class, () -> setUp(DOMAIN_INPUT_INVALID_1, PROBLEM_INPUT_BASIC_1));
    }

    @Test
    public void testParser_basicInput_invalid_2() throws FileNotFoundException, ParseException {
        assertThrows(ParseException.class, () -> setUp(DOMAIN_INPUT_INVALID_2, PROBLEM_INPUT_BASIC_1));
    }

    @Test
    public void testParser_basicInput_problem_1() throws FileNotFoundException, ParseException {
        setUp(DOMAIN_INPUT_BASIC_1, PROBLEM_INPUT_BASIC_1);
        assertEquals("pfile01", problem.getName());
        assertEquals(8, problem.getObjects().size());
        assertEquals(2, problem.getHtn().getSubtasks().size());
        assertEquals(9, problem.getInit().size());
    }

    @Test
    public void testParser_problem_basicInput_from_preliminaries_valid() throws FileNotFoundException, ParseException {
        setUp(DOMAIN_INPUT_BASIC_1, PROBLEM_BASIC_FROM_PRELIMINARIES);
        assertEquals("pfile01", problem.getName());
    }
}
