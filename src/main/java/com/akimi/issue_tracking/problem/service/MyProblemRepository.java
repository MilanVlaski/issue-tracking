package com.akimi.issue_tracking.problem.service;

import com.akimi.issue_tracking.problem.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByState(String state);
    List<Problem> findByStateAndEngineersEmail(String state, String email);
    List<Problem> findByEngineersEmail(String email);
}
