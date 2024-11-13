package com.akimi.issue_tracking.problem;

import com.akimi.issue_tracking.entities.Application;
import com.akimi.issue_tracking.entities.Purchase;
import com.akimi.issue_tracking.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Set;

@Controller
public class ProblemPages {

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/reportProblem")
    public String reportProblem(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = em.createQuery("select u from User u where u.email = :email", User.class)
                     .setParameter("email", email)
                     .getSingleResult();
        //        var apps = new ArrayList<Application>();
//
//        while (purchases.hasNext()) {
//            apps.add(purchases.next().getApplication());
//        }

        var purchases = user.getPurchases();
        model.addAttribute("purchases", purchases);
        return "reportProblem";
    }

    @GetMapping("/application/{appId}/reportProblem")
    public String reportProblem(@PathVariable String appId, Model model) {
        return "describeProblem";
    }

    @PostMapping("/application/{appId}/reportProblem")
    public String reportProblemPost(@PathVariable String appId, Model model) {
        return "redirect:/application/" + appId + "/reportProblem";
    }

    @GetMapping("/engineer/problems")
    public String index(Model model) {
        return "engineerProblems";
    }


}
