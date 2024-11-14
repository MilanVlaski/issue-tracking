package com.akimi.issue_tracking.problem;

import com.akimi.issue_tracking.entities.Action;
import com.akimi.issue_tracking.entities.Application;
import com.akimi.issue_tracking.entities.Problem;
import com.akimi.issue_tracking.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProblemProcessing {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void report(ProblemReport problemReport, String appId, User user) {
        AtomicInteger id = new AtomicInteger(1);
        var actions = Arrays.stream(problemReport.getActions().trim().split("\n"))
                            .map(String::trim)
                            .filter(line -> !line.isEmpty())
                            .map(String::trim)
                            .map(line -> new Action(id.getAndIncrement(), line))
                            .toList();

        var application = em.find(Application.class, appId);
        var problem = new Problem()
                .setDescription(problemReport.getDescription())
                .addApplication(application)
                .setState(ProblemState.REPORTED.name)
                .setUser(user);
        problem.getActions().addAll(actions);

        em.persist(application);
        em.persist(user);
        em.persist(problem);
    }
}
