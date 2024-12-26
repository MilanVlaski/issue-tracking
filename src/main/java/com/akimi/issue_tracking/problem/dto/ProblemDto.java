package com.akimi.issue_tracking.problem.dto;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProblemDto {

    private Integer id;
    private User user;
    private Application application;
    private String state;
    private String engState;
    private String description;
    private boolean mine;
}
