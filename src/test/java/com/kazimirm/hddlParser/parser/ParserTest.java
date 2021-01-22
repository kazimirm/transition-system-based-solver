package com.kazimirm.hddlParser.parser;

import com.kazimirm.hddlParser.hddlObjects.Domain;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    private final String BASIC_INPUT_FILE = "basic_input.txt";
    @Test
    public void testParser_basicInput_valid() throws FileNotFoundException, ParseException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(BASIC_INPUT_FILE).getFile());
        InputStream targetStream = new FileInputStream(file);


        ParserHDDL parser = new ParserHDDL(targetStream);

        Domain domain = parser.start();
        assertEquals("domain_htn", domain.getName());
        assertEquals(3, domain.getRequirements().size());
        assertEquals(6, domain.getTypes().size());
        assertEquals(5, domain.getPredicates().size());
        assertEquals(4, domain.getTasks().size());
    }
}
