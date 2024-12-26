package com.akimi.issue_tracking.problem.problemPages;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.problem.dto.ProblemReport;
import com.akimi.issue_tracking.problem.service.ProblemProcessing;
import com.akimi.issue_tracking.security.CurrentUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.akimi.issue_tracking.problem.ProblemPages.redirectToReferer;

@Controller
public class ReportProblem {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CurrentUser currentLogin;

    @Autowired
    private ProblemProcessing problemProcessing;

    @GetMapping("/reportProblem")
    public String reportProblem(Model model) {
        var purchases = currentLogin.user().getPurchases();
        model.addAttribute("purchases", purchases);
        return "reportProblem";
    }

    @GetMapping("/application/{appId}/reportProblem")
    public String reportProblem() {
        return "describeProblem";
    }

    @PostMapping("/application/{appId}/reportProblem")
    public String reportProblemPost(@PathVariable String appId,
            @ModelAttribute ProblemReport problemReport,
            HttpServletRequest request, RedirectAttributes redirectAttributes
    ) {
        var application = em.find(Application.class, appId);
        var user = currentLogin.user();
        problemProcessing.report(problemReport, application, user);
        redirectAttributes.addFlashAttribute("problemStatus", "success");
        return redirectToReferer(request);
    }
}
