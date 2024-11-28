package com.akimi.issue_tracking.problem;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.problem.dto.AnswerDto;
import com.akimi.issue_tracking.problem.dto.ProblemReport;
import com.akimi.issue_tracking.security.CurrentUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProblemPages {

    @Autowired
    private CurrentUser currentUser;
    ;

    @Autowired
    private ProblemProcessing problemProcessing;

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/reportProblem")
    public String reportProblem(Model model) {
        var purchases = currentUser.currentUser().getPurchases();
        model.addAttribute("purchases", purchases);
        return "reportProblem";
    }

    @GetMapping("/application/{appId}/reportProblem")
    public String reportProblem() {
        return "describeProblem";
    }

    @PostMapping("/application/{appId}/reportProblem")
    public String reportProblemPost(@PathVariable String appId,
            @ModelAttribute ProblemReport problemReport
    ) {
        var application = em.find(Application.class, appId);
        var user = currentUser.currentUser();
        problemProcessing.report(problemReport, application, user);
        return "redirect:/application/" + appId + "/reportProblem";
    }

    @GetMapping("/engineer/problems")
    public String index(Model model) {
        var problems = em.createQuery("select p from Problem p", Problem.class)
                         .getResultList();
        model.addAttribute("problems", problems);
        return "engineerProblems";
    }

    @GetMapping("/engineer/problems/mine")
    public String mine(Model model) {
        var currentEngineer = currentUser.currentEngineer();
        model.addAttribute(
                "problems",
                em.createQuery("select p from Problem p" +
                          " join p.engineers e where e.email = :email", Problem.class)
                  .setParameter("email", currentEngineer.getEmail())
                  .getResultList()
        );
        return "engineerProblems";
    }

    @GetMapping("/problems")
    public String problems(Model model) {
        model.addAttribute(
                "problems",
                em.createQuery("select p from Problem p " +
                                  "left join fetch p.answers where p.user = :user",
                          Problem.class)
                  .setParameter("user", currentUser.currentUser())
                  .getResultList()
        );
        return "problems";
    }

    @GetMapping("/engineer/problems/{problemId}")
    public String answerProblem(@PathVariable String problemId, Model model) {
        var problem = em.find(Problem.class, problemId);
        model.addAttribute("problem", problem);
        model.addAttribute("actions", problem.getActions());
        return "answerProblem";
    }

    @PostMapping("/engineer/problems/{problemId}/answer")
    public String answerProblemPost(@PathVariable String problemId,
            @ModelAttribute AnswerDto answer) {
        problemProcessing.solveProblem(em.find(Problem.class, problemId),
                answer.toEntity(),
                currentUser.currentEngineer()
        );
        return "redirect:/engineer/problems/" + problemId;
    }

    @PostMapping("/engineer/problems/{problemId}/assignEngineer")
    public String assignEngineer(@PathVariable String problemId) {
        problemProcessing.assignEngineerToProblem(
                currentUser.currentEngineer(),
                em.find(Problem.class, problemId)
        );
        return "redirect:/engineer/problems";
    }

}
