package com.akimi.issue_tracking.problem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByState(String state);
}
