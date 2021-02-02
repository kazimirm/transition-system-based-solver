package com.kazimirm.hddlParser.parser;

import com.kazimirm.hddlParser.hddlObjects.Domain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    private ParserHDDL parser;

    private final String BASIC_INPUT_FILE = "basic_input.txt";
    private final String BASIC_INPUT_FILE_INVALID_1 = "basic_input_invalid_1.txt"; // unexpected text after requirements
    private final String BASIC_INPUT_FILE_INVALID_2 = "basic_input_invalid_1.txt"; // wrong space

    @BeforeAll
    static void setup(){
        ParserHDDL parser = new ParserHDDL(InputStream.nullInputStream());
    }

    @Test
    public void testParser_basicInput_valid() throws FileNotFoundException, ParseException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(BASIC_INPUT_FILE).getFile());
        InputStream targetStream = new FileInputStream(file);

        parser.ReInit(targetStream);

        Domain domain = parser.start();
        assertEquals("domain_htn", domain.getName());
        assertEquals(3, domain.getRequirements().size());
        assertEquals(6, domain.getTypes().size());
        assertEquals(5, domain.getPredicates().size());
        assertEquals(4, domain.getTasks().size());
    }

    @Test
    public void testParser_basicInput_invalid_1() throws FileNotFoundException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(BASIC_INPUT_FILE_INVALID_1).getFile());
        InputStream targetStream = new FileInputStream(file);

        parser.ReInit(targetStream);
        assertThrows(ParseException.class, () -> parser.start());
    }

    @Test
    public void testParser_basicInput_invalid_2() throws FileNotFoundException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(BASIC_INPUT_FILE_INVALID_2).getFile());
        InputStream targetStream = new FileInputStream(file);

        parser.ReInit(targetStream);
        assertThrows(ParseException.class, () -> parser.start());
    }

}
