package com.kazimirm.transitionSystemBasedHtnSolver;

import com.kazimirm.transitionSystemBasedHtnSolver.answerExtractor.AnswerExtractor;
import com.kazimirm.transitionSystemBasedHtnSolver.answerExtractor.graphRepresentation.Graph;
import com.kazimirm.transitionSystemBasedHtnSolver.dataEnricher.ProblemEnricher;
import com.kazimirm.transitionSystemBasedHtnSolver.encoder.Z3Encoder;
import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.Domain;
import com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects.Problem;
import com.kazimirm.transitionSystemBasedHtnSolver.parser.ParseException;
import com.kazimirm.transitionSystemBasedHtnSolver.parser.ParserHDDL;
import com.microsoft.z3.Expr;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransitionSystemBasedHtnSolver {

    private static final String DOT_OPTION = "-dot";

    public static void main (String[] args){
        if (args.length < 2){
            throw new IllegalArgumentException("The valid parameters are paths to PD and PP files. Optionally You can add '-dot' " +
                    "as a 3rd parameter to add graph dot format. For more info please check the documentation!");
        }
        String pD = args[0];
        String pP = args[1];

        // Implicitly we print the output in standard output format however we the user a chance to print our
        // answer with help actions in dot format as well. In this case, there should be a third parameter equal to
        // "-dot" value
        List<String> argsList = new ArrayList<>(Arrays.asList(args));
        boolean addDotToPrint = argsList.stream().anyMatch(DOT_OPTION::equalsIgnoreCase);


        long startTime = System.nanoTime();
        try (InputStream dI = Files.newInputStream(Paths.get(pD));
             InputStream pI = Files.newInputStream(Paths.get(pP))) {

            ParserHDDL parser = new ParserHDDL(dI);
            Domain domain = parser.parseDomain();
            parser.ReInit(pI);
            Problem problem = parser.parseProblem();

            ProblemEnricher enricher = new ProblemEnricher(domain, problem);

            Z3Encoder encoder = new Z3Encoder(enricher.getDomain(), enricher.getProblem());
            encoder.encodeToZ3ExpressionsAndGetResult();
            AnswerExtractor extractor = new AnswerExtractor(encoder);
            Graph graph = extractor.getGraphFromAnswer();
            System.out.println(graph.getStandardOutput());
            if (addDotToPrint){
                System.out.println();
                System.out.println("Dot graph format:");
                System.out.println(graph.getGraphInDotFormat());
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            long endTime = System.nanoTime();
            long elapsedTime = (endTime - startTime);
            double duration = (double) elapsedTime / 1_000_000_000;
            System.out.println("Execution took '"+ duration +"' seconds");
        }

    }
}
