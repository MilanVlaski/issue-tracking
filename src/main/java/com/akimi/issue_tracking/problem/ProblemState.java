package com.akimi.issue_tracking.problem;

public enum ProblemState {

    REPORTED("Prijavljen", "Reported"),
    ASSIGNED("Preuzet", "Assigned to Engineer"),
    SOLVING("Rješava se", "Being Resolved"),
    SOLVED("Riješen", "Solved");

    public final String name;
    public final String engName;

    ProblemState(String name,String engName) {
        this.name = name;
        this.engName = engName;
    }
}
