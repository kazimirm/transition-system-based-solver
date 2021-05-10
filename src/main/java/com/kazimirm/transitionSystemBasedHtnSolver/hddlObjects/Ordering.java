package com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects;

public class Ordering {
    //todo: is there a better way how to represent it?
    private String subtaskBefore;
    private String subtaskAfter;

    public Ordering() {
    }

    public String getSubtaskBefore() {
        return subtaskBefore;
    }

    public void setSubtaskBefore(String subtaskBefore) {
        this.subtaskBefore = subtaskBefore;
    }

    public String getSubtaskAfter() {
        return subtaskAfter;
    }

    public void setSubtaskAfter(String subtaskAfter) {
        this.subtaskAfter = subtaskAfter;
    }
}
