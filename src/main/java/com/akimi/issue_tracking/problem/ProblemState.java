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

    public static ProblemState fromDbName(String name) {
        for (ProblemState problemState : ProblemState.values()) {
            if (problemState.name.equals(name)) {
                return problemState;
            }
        }
        throw new IllegalArgumentException("Invalid problem state: " + name);
    }
}
