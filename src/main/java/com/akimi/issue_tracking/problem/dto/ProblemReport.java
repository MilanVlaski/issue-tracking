package com.akimi.issue_tracking.problem.dto;

import com.akimi.issue_tracking.problem.Action;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public record ProblemReport(String description, String actions) {
    public List<Action> parseActions() {
        var ordinalNumber = new AtomicInteger(1);
        return Arrays.stream(actions.trim().split("\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(String::trim)
                .map(line -> new Action(ordinalNumber.getAndIncrement(), line))
                .toList();
    }
}

