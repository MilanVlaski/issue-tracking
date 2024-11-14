package com.akimi.issue_tracking.problem;

import com.akimi.issue_tracking.application.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
        var user = currentUser();
        problemProcessing.report(problemReport, appId, user);
        return "redirect:/application/" + appId + "/reportProblem";
    }

    private User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return em.createQuery("select u from User u where u.email = :email", User.class)
                 .setParameter("email", email)
                 .getSingleResult();
    }

    @GetMapping("/engineer/problems")
    public String index(Model model) {
        return "engineerProblems";
    }


}
