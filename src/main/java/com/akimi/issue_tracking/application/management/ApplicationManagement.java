package com.akimi.issue_tracking.application.management;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.problem.ProblemPages;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.akimi.issue_tracking.problem.ProblemPages.redirectToReferer;

@Controller
public class ApplicationManagement {

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/engineer/application/add")
    public String registerApplication() {
        return "addApplication";
    }
    @PostMapping("/engineer/application/add")
    public String addApplication(@ModelAttribute Application application,
            HttpServletRequest request) {
        em.persist(application);
        return redirectToReferer(request);
    }


}
