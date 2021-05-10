package com.kazimirm.transitionSystemBasedHtnSolver.hddlObjects;

import java.util.stream.Collectors;

public class Subtask {
    private String name;
    private Task task;

    public Subtask() {
    }

    public Subtask(String name, Task task) {
        this.name = name;
        this.task = task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }


}
