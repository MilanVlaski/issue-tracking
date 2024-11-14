package com.akimi.issue_tracking.problem;

public enum ProblemState {

    REPORTED("Prijavljen"),
    ASSIGNED("Preuzet"),
    SOLVING("Rješava se"),
    SOLVED("Riješen");

    public final String name;

    ProblemState(String name) {
        this.name = name;
    }
}
