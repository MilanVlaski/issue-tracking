package com.akimi.issue_tracking.problem.dto;

import com.akimi.issue_tracking.problem.engineer.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class AnswerDto {
    private String answer;

    public Answer toEntity() {

        return new Answer().setDescription(answer);
    }
}
