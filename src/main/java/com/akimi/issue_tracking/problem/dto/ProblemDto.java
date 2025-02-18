package com.akimi.issue_tracking.problem.dto;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public record ProblemDto(
        Integer id,
        User user,
        Application application,
        String state,
        String description,
        boolean mine
) {
}
