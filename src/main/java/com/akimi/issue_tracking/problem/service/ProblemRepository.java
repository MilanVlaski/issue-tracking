package com.akimi.issue_tracking.problem.service;

import com.akimi.issue_tracking.problem.Problem;
import com.akimi.issue_tracking.problem.ProblemState;
import com.akimi.issue_tracking.problem.dto.ProblemDto;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByState(ProblemState state);

    @Query("""
        SELECT new com.akimi.issue_tracking.problem.dto.ProblemDto(
            p.id, p.user, p.application, p.state, p.description,
            CASE WHEN ps.engineer = :engineer THEN true ELSE false END
        )
        FROM Problem p
        LEFT JOIN ProblemSolver ps ON ps.problem = p AND ps.engineer = :engineer
    """)
    List<ProblemDto> findAllBelongingTo(@Param("engineer") Engineer engineer);

    @Query("""
        SELECT new com.akimi.issue_tracking.problem.dto.ProblemDto(
            p.id, p.user, p.application, p.state, p.description,
            CASE WHEN ps.engineer = :engineer THEN true ELSE false END
        )
        FROM Problem p
        LEFT JOIN ProblemSolver ps ON ps.problem = p AND ps.engineer = :engineer
        WHERE p.state = :state
    """)
    List<ProblemDto> findAllBelongingToEngineerByState(@Param("engineer") Engineer engineer, @Param("state") ProblemState state);
}
