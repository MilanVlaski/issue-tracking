package com.akimi.issue_tracking.problem.dto;

import com.akimi.issue_tracking.problem.engineer.Answer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDto {
    private String answer;
    private String problemState;
    public Answer toEntity() {
        return new Answer(answer);
    }
}
