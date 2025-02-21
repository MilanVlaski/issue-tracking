package com.akimi.issue_tracking.problem;

import com.akimi.issue_tracking.problem.dto.ProblemPatchBody;
import com.akimi.issue_tracking.problem.service.ProblemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ProblemController {

    private final ProblemRepository problemRepository;

    public ProblemController(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @PatchMapping("/engineer/problems/{problemId}")
    public ResponseEntity<String> updateState(
            @PathVariable("problemId") String problemId,
            @RequestBody ProblemPatchBody requestBody) {

        var problem = problemRepository.findById(Long.valueOf(problemId));

        if (problem.isPresent()) {
            var prob = problem.get();
            prob.setState(ProblemState.valueOfIgnoreCase(requestBody.state()));
            problemRepository.save(prob);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
