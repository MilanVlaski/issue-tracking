package com.akimi.issue_tracking.problem.dto;

import com.akimi.issue_tracking.problem.engineer.Answer;
import lombok.Getter;
import lombok.Setter;

public record AnswerDto(String answer, String problemState) {
    public Answer toEntity() {
        return new Answer(answer);
    }
}
