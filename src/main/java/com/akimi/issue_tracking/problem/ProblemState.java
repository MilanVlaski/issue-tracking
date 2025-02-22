package com.akimi.issue_tracking.problem;


public enum ProblemState {

    REPORTED("Prijavljen", "Reported"),
    ASSIGNED("Preuzet", "Assigned to Engineer"),
    SOLVING("Rješava se", "Being Resolved"),
    SOLVED("Riješen", "Solved");

    final String serbian;
    final String english;

    ProblemState(String serbian, String english) {
        this.serbian = serbian;
        this.english = english;
    }

    public static ProblemState valueOfIgnoreCase(String value) {
        for (ProblemState state : values()) {
            if (state.name().equalsIgnoreCase(value) ||
                    state.english.equalsIgnoreCase(value) ||
                    state.serbian.equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown state: " + value);
    }

    public String getEnglish() {
        return english;
    }
}
