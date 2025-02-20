package com.akimi.issue_tracking.problem.dto;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.ProblemState;

public record ProblemDto(
        Integer id,
        User user,
        Application application,
        ProblemState state,
        String description,
        boolean mine
) {
}
