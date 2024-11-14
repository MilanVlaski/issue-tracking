package com.akimi.issue_tracking.problem;

public class ProblemReport {

    private String description;
    private String actions;

    public String getDescription() {
        return description;
    }

    public ProblemReport setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getActions() {
        return actions;
    }

    public ProblemReport setActions(String actions) {
        this.actions = actions;
        return this;
    }
}
