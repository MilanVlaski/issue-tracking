package com.akimi.issue_tracking.problem;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.dto.AnswerDto;
import com.akimi.issue_tracking.problem.engineer.Answer;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProblemPages {

    @Autowired
    private ProblemProcessing problemProcessing;

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/reportProblem")
    public String reportProblem(Model model) {
        var purchases = currentUser().getPurchases();
        model.addAttribute("purchases", purchases);
        return "reportProblem";
    }

    @GetMapping("/application/{appId}/reportProblem")
    public String reportProblem(@PathVariable String appId, Model model) {
        return "describeProblem";
    }

    @PostMapping("/application/{appId}/reportProblem")
    public String reportProblemPost(@PathVariable String appId, Model model,
            @ModelAttribute ProblemReport problemReport) {
        var application = em.find(Application.class, appId);
        var user = currentUser();
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
        var currentEngineer = currentEngineer();
        var myProblems = em.createQuery("select p from Problem p join p.engineers e " +
                                   "where e.email = :email", Problem.class)
                           .setParameter("email", currentEngineer.getEmail())
                           .getResultList();
        model.addAttribute("problems", myProblems);
        return "engineerProblems";
    }

    @GetMapping("/problems")
    public String problems(Model model) {
        var problems = em.createQuery("select p from Problem p left join fetch p.answers where p.user = :user",
                                 Problem.class)
                         .setParameter("user", currentUser())
                         .getResultList();
        model.addAttribute("problems", problems);
        return "problems";
    }

    @GetMapping("/engineer/problems/{problemId}")
    public String answerProblem(@PathVariable String problemId, Model model) {
        var problem = em.find(Problem.class, problemId);
        model.addAttribute("problem", problem);
        var actions = problem.getActions();
        model.addAttribute("actions", actions);
        return "answerProblem";
    }

    @PostMapping("/engineer/problems/{problemId}/answer")
    public String answerProblemPost(@PathVariable String problemId, Model model,
            @ModelAttribute AnswerDto answer, HttpServletRequest request) {
        var problem = em.find(Problem.class, problemId);
        problemProcessing.solveProblem(problem, answer.toEntity(), currentEngineer());
        return "redirect:/engineer/problems/" + problemId;
    }

    @PostMapping("/engineer/problems/{problemId}/assignEngineer")
    public String assignEngineer(@PathVariable String problemId) {
        var problem = em.find(Problem.class, problemId);
        var engineer = currentEngineer();
        problemProcessing.assignEngineerToProblem(engineer, problem);
        return "redirect:/engineer/problems";
    }

    public User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return em.createQuery("select u from User u where u.email = :email", User.class)
                 .setParameter("email", email)
                 .getSingleResult();
    }

    public Engineer currentEngineer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return em.createQuery("select e from Engineer e where e.email = :email", Engineer.class)
                 .setParameter("email", email)
                 .getSingleResult();
    }
}
