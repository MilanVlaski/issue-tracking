package com.akimi.issue_tracking.security.dto;

import com.akimi.issue_tracking.problem.engineer.Engineer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class EngineerRegistration {

    private String email;
    private String password;
    private String name;
    private String education;
    private String monthlySalary;
    private String employedFrom;
    private String employedUntil;

    public Engineer toEntity() {
        return new Engineer(name, education, LocalDate.parse(employedFrom, DateTimeFormatter.ISO_LOCAL_DATE),
                Double.parseDouble(monthlySalary), email, password);
    }
}
