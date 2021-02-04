package com.kazimirm.hddlParser.parser;

import com.kazimirm.hddlParser.hddlObjects.Domain;
import com.kazimirm.hddlParser.hddlObjects.Problem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    private ParserHDDL parser;

    private final String DOMAIN_INPUT_BASIC_1 = "domain_input_basic_1.txt";
    private final String DOMAIN_BASIC_FROM_PRELIMINARIES = "domain_basic_from_preliminaries.txt";
    private final String DOMAIN_INPUT_INVALID_1 = "domain_input_invalid_2.txt"; // unexpected text after requirements
    private final String DOMAIN_INPUT_INVALID_2 = "domain_input_invalid_1.txt"; // wrong space
    private final String PROBLEM_INPUT_BASIC_1 = "problem_input_basic_1.txt";
    private final String PROBLEM_BASIC_FROM_PRELIMINARIES = "problem_basic_from_preliminaries.txt";

    @BeforeAll
    static void setup(){
        ParserHDDL parser = new ParserHDDL(InputStream.nullInputStream());
    }

    @Test
    public void testParser_domain_basicInput_from_preliminaries_valid() throws FileNotFoundException, ParseException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(DOMAIN_BASIC_FROM_PRELIMINARIES).getFile());
        InputStream targetStream = new FileInputStream(file);

        parser.ReInit(targetStream);

        Domain domain = parser.parseDomain();
        assertEquals("domain_htn", domain.getName());
    }

    @Test
    public void testParser_basicInput_valid() throws FileNotFoundException, ParseException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(DOMAIN_INPUT_BASIC_1).getFile());
        InputStream targetStream = new FileInputStream(file);

        parser.ReInit(targetStream);

        Domain domain = parser.parseDomain();
        assertEquals("domain_htn", domain.getName());
        assertEquals(3, domain.getRequirements().size());
        assertEquals(6, domain.getTypes().size());
        assertEquals(5, domain.getPredicates().size());
        assertEquals(4, domain.getTasks().size());
    }

    @Test
    public void testParser_basicInput_invalid_1() throws FileNotFoundException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(DOMAIN_INPUT_INVALID_1)).getFile());
        InputStream targetStream = new FileInputStream(file);

        parser.ReInit(targetStream);
        assertThrows(ParseException.class, () -> parser.parseDomain());
    }

    @Test
    public void testParser_basicInput_invalid_2() throws FileNotFoundException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(DOMAIN_INPUT_INVALID_2)).getFile());
        InputStream targetStream = new FileInputStream(file);

        parser.ReInit(targetStream);
        assertThrows(ParseException.class, () -> parser.parseDomain());
    }

    @Test
    public void testParser_basicInput_problem_1() throws FileNotFoundException, ParseException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(PROBLEM_INPUT_BASIC_1)).getFile());
        InputStream targetStream = new FileInputStream(file);

        parser.ReInit(targetStream);
        Problem problem = parser.parseProblem();
        assertEquals("pfile01", problem.getName());
        assertEquals(8, problem.getObjects().size());
        assertEquals(2, problem.getHtn().getSubtasks().size());
        assertEquals(9, problem.getInit().size());
    }

    @Test
    public void testParser_problem_basicInput_from_preliminaries_valid() throws FileNotFoundException, ParseException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(PROBLEM_BASIC_FROM_PRELIMINARIES)).getFile());
        InputStream targetStream = new FileInputStream(file);

        parser.ReInit(targetStream);
        Problem problem = parser.parseProblem();
        assertEquals("pfile01", problem.getName());
    }

    @Test
    public void testParser_problem_giving_valid_domain_instead_of_problem() throws FileNotFoundException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(DOMAIN_INPUT_BASIC_1)).getFile());
        InputStream targetStream = new FileInputStream(file);

        parser.ReInit(targetStream);
        assertThrows(ParseException.class, () -> parser.parseProblem());
    }
}
