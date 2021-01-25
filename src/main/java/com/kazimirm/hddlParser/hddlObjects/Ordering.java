package com.kazimirm.hddlParser.hddlObjects;

public class Ordering {
    //todo: is there a better way how to represent it?
    private String taskBefore;
    private String taskAfter;

    public Ordering() {
    }

    public String getTaskBefore() {
        return taskBefore;
    }

    public void setTaskBefore(String taskBefore) {
        this.taskBefore = taskBefore;
    }

    public String getTaskAfter() {
        return taskAfter;
    }

    public void setTaskAfter(String taskAfter) {
        this.taskAfter = taskAfter;
    }
}
