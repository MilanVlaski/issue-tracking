package com.akimi.issue_tracking.problem.dto;

import com.akimi.issue_tracking.problem.Problem;
import com.akimi.issue_tracking.problem.engineer.Patch;
import lombok.Getter;

import java.util.List;

@Getter
public class ProblemWithPatches {

    private final Problem problem;
    private final List<Patch> patches;

    public ProblemWithPatches(Problem problem) {
        this.problem = problem;
        this.patches = problem.getProblemSolvers().stream()
                              .flatMap(ps -> ps.getPatches().stream())
                              .toList();
    }

}
