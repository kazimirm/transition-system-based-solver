package com.kazimirm.transitionSystemBasedHtnSolver.answerExtractor;

import com.kazimirm.transitionSystemBasedHtnSolver.answerExtractor.graphRepresentation.Graph;
import com.kazimirm.transitionSystemBasedHtnSolver.encoder.Z3Encoder;
import com.microsoft.z3.Expr;

public class AnswerExtractor {
    Z3Encoder encoder;
    Graph graph;

    public AnswerExtractor(Z3Encoder encoder) {
        this.encoder = encoder;
    }

    public Graph getGraphFromAnswer() {
        Graph graph = new Graph(encoder.getProblem().getName(), encoder.getObjectToInt(), encoder.getAnswer(), encoder.isSatisfiable());
        return graph;
    }
}
