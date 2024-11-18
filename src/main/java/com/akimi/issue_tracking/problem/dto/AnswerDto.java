package com.akimi.issue_tracking.problem.dto;

import com.akimi.issue_tracking.problem.engineer.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

public class AnswerDto {
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Answer toEntity() {
        return new Answer().setDescription(answer);
    }
}
