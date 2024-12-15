package com.akimi.issue_tracking.problem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
public class ProblemController {

    private final MyProblemRepository myProblemRepository;
    @PersistenceContext
    private EntityManager em;

    public ProblemController(MyProblemRepository myProblemRepository) {
        this.myProblemRepository = myProblemRepository;
    }

    @PatchMapping("/engineer/problems/{problemId}")
    public ResponseEntity<String> updateState(
            @PathVariable("problemId") String problemId,
            @RequestBody ProblemPatchBody requestBody) {

        var problem = myProblemRepository.findById(Long.valueOf(problemId));

        if (problem.isPresent()) {
            var prob = problem.get();
            prob.setState(ProblemState.fromEngName(requestBody.getState()));
            myProblemRepository.save(prob);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
