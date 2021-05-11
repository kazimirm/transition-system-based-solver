package com.kazimirm.transitionSystemBasedHtnSolver;

import com.kazimirm.transitionSystemBasedHtnSolver.dataEnricher.ProblemEnricher;
import com.kazimirm.transitionSystemBasedHtnSolver.encoder.Z3Encoder;
import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.Domain;
import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.Problem;
import com.kazimirm.transitionSystemBasedHtnSolver.parser.ParseException;
import com.kazimirm.transitionSystemBasedHtnSolver.parser.ParserHDDL;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TransitionSystemBasedHtnSolver {
    public static void main (String[] args){
        if (args.length != 2){
            throw new IllegalArgumentException("The valid parameters are paths to PD and PP files. " +
                    "For more info, check the documentation!");
        }
        String pD = args[0];
        String pP = args[1];

        try (InputStream dI = Files.newInputStream(Paths.get(pD));
             InputStream pI = Files.newInputStream(Paths.get(pP))) {

            ParserHDDL parser = new ParserHDDL(dI);
            Domain domain = parser.parseDomain();
            parser.ReInit(pI);
            Problem problem = parser.parseProblem();

            ProblemEnricher enricher = new ProblemEnricher(domain, problem);

            Z3Encoder encoder = new Z3Encoder(enricher.getDomain(), enricher.getProblem());
            encoder.encodeToZ3ExpressionsAndGetResult();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
