package com.akimi.issue_tracking;

import com.akimi.issue_tracking.entities.Application;
import com.akimi.issue_tracking.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

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
        var purchases = user.getPurchases().iterator();
        var apps = new ArrayList<Application>();

        while (purchases.hasNext()) {
            apps.add(purchases.next().getApplication());
        }

        model.addAttribute("apps", apps);
        return "reportProblem";
    }
}
