package com.akimi.issue_tracking.problem.dto;

import com.akimi.issue_tracking.application.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class UserRegistration {

    private String name;
    private String email;
    private String password;
    private String birthYear;
    private String phoneNumber;
    private String location;

    public User toEntity() {
        return new User(name, email, password, LocalDate.parse(birthYear, DateTimeFormatter.ISO_LOCAL_DATE), location, phoneNumber);
    }
}
