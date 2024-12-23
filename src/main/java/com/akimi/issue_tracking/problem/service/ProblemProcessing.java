package com.akimi.issue_tracking.problem.service;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.purchase.SupportType;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.application.service.AppDistribution;
import com.akimi.issue_tracking.problem.Action;
import com.akimi.issue_tracking.problem.Problem;
import com.akimi.issue_tracking.problem.ProblemState;
import com.akimi.issue_tracking.problem.dto.ProblemReport;
import com.akimi.issue_tracking.problem.engineer.Answer;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import com.akimi.issue_tracking.problem.engineer.Patch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProblemProcessing {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Problem report(ProblemReport problemReport, Application application, User user) {
        var actions = parseActions(problemReport.getActions());
        var problem = new Problem(problemReport.getDescription(), application, user, actions);
        em.persist(application);
        em.persist(user);
        em.persist(problem);
        return problem;
    }

    private List<Action> parseActions(String actions1) {
        var ordinalNumber = new AtomicInteger(1);
        return Arrays.stream(actions1.trim().split("\n"))
                     .map(String::trim)
                     .filter(line -> !line.isEmpty())
                     .map(String::trim)
                     .map(line -> new Action(ordinalNumber.getAndIncrement(), line))
                     .toList();
    }

    @Transactional
    public void answerProblem(Problem problem, Answer answer, Engineer engineer, ProblemState problemState) {
        problem.add(answer);
        engineer.add(answer);
        problem.setState(problemState);
        em.persist(answer);
    }

    @Transactional
    public void assignEngineerToProblem(Engineer engineer, Problem problem) {
        problem.assignEngineer(engineer);
        problem.setState(ProblemState.ASSIGNED);
        em.persist(problem);
        em.persist(engineer);
    }

    @Autowired
    private AppDistribution appDistribution;

    @Transactional
    public Application patchProblem(Problem problem, Patch patch, Engineer engineer) {
        var newApp = engineer.patchProblem(patch, problem);
        distributeNewAppToOldOwners(problem, newApp);

        em.persist(newApp);
        em.persist(engineer);
        em.persist(problem);
        em.persist(patch);
        return newApp;
    }

    private void distributeNewAppToOldOwners(Problem problem, Application newApp) {
        var previousSupportType = getPreviousSupportType(problem);
        var purchases = appDistribution.sendApplicationToPreviousUsers(newApp, previousSupportType);
        purchases.forEach(p -> em.persist(p));
    }

    private SupportType getPreviousSupportType(Problem problem) {
        return em.createQuery("select p.supportType " +
                          "from Purchase p " +
                          "join p.application app " +
                          "where app = :problemApplication",
                  SupportType.class)
         .setParameter("problemApplication", problem.getApplication())
         .getSingleResult();
    }
}
