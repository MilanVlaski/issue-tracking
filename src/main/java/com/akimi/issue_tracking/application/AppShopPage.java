package com.akimi.issue_tracking.application;

import com.akimi.issue_tracking.application.purchase.PurchasingService;
import com.akimi.issue_tracking.application.purchase.Support;
import com.akimi.issue_tracking.problem.ProblemPages;
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

import java.util.ArrayList;

import static com.akimi.issue_tracking.problem.ProblemPages.redirectToReferer;

@Controller
public class AppShopPage {

    @Autowired
    private CurrentUser currentUser;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private PurchasingService purchasingService;


    @GetMapping("/")
    public String index(Model model) {
        var apps = new ArrayList<Application>(
                em.createQuery("select a from Application a").getResultList()
        );

        model.addAttribute("apps", apps);
        return "index";
    }

    @GetMapping("/application/{appId}/buy")
    public String buy(Model model, @PathVariable String appId) {
        var app = em.find(Application.class, appId);
        var supportTypes = em.createQuery("select s from SupportType s").getResultList();
        var ownedByUser = currentUser.user().ownsApplication(app);

        model.addAttribute("app", app);
        model.addAttribute("supportTypes", supportTypes);
        model.addAttribute("ownedByUser", ownedByUser);
        return "buy";
    }

    @PostMapping("/application/{appId}/buy")
    public String processPurchase(@PathVariable String appId, @ModelAttribute Support support,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        var user = currentUser.user();
        var application = em.find(Application.class, Integer.valueOf(appId));

        boolean success = purchasingService.purchaseApp(support.getSupport(), application, user);
        if (success) {
            redirectAttributes.addFlashAttribute("purchaseStatus", "success");
        } else {
            redirectAttributes.addFlashAttribute("purchaseStatus", "failure");
        }

        return redirectToReferer(request);
    }

    @PostMapping("/application/{appId}/install")
    public String installApp(@PathVariable String appId, Model model,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // this is a dummy for now
        redirectAttributes.addFlashAttribute("installed", true);
        return redirectToReferer(request);
    }



}
