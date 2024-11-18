package com.akimi.issue_tracking.problem;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.engineer.Answer;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProblemProcessing {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void report(ProblemReport problemReport, Application application , User user) {
        var actions = parseActions(problemReport.getActions());
        var problem = new Problem(problemReport.getDescription(), application, user, actions);
        em.persist(problem);
    }

    private List<Action> parseActions(String actions1) {
        var id = new AtomicInteger(1);
        return Arrays.stream(actions1.trim().split("\n"))
                     .map(String::trim)
                     .filter(line -> !line.isEmpty())
                     .map(String::trim)
                     .map(line -> new Action(id.getAndIncrement(), line))
                     .toList();
    }

    @Transactional
    public void solveProblem(Problem problem, Answer answer, Engineer engineer) {
        problem.add(answer);
        engineer.add(answer);
        problem.setState(ProblemState.SOLVED.name);
        em.persist(answer);
    }
}
