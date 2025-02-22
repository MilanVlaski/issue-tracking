package com.akimi.issue_tracking.problem.service;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.application.service.AppDistribution;
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

@Service
public class ProblemProcessing {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Problem report(ProblemReport problemReport, Application application, User user) {
        var problem = new Problem(problemReport, application, user);
        em.persist(application);
        em.persist(user);
        em.persist(problem);
        return problem;
    }

    @Transactional
    public void answerProblem(Problem problem, Answer answer, Engineer engineer, ProblemState problemState) {
        engineer.answer(problem, answer);
        problem.setState(problemState);
        em.persist(answer);
    }

    @Autowired
    private AppDistribution appDistribution;

    @Transactional
    public Application patchProblem(Problem problem, Patch patch, Engineer engineer) {
        var newApp = engineer.patchProblem(patch, problem);
        appDistribution.sendApplicationToPreviousUsers(newApp);

        em.persist(newApp);
        em.persist(patch);
        return newApp;
    }

}
