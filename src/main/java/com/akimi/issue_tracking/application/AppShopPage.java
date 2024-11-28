package com.akimi.issue_tracking.application;

import com.akimi.issue_tracking.security.CurrentUser;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

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
        var supportTypes = new ArrayList<SupportType>(
                em.createQuery("select s from SupportType s").getResultList()
        );

        model.addAttribute("app", app);
        model.addAttribute("supportTypes", supportTypes);
        return "buy";
    }

    @PostMapping("/application/{appId}/buy")
    public String processPurchase(@PathVariable String appId, @ModelAttribute Support support,
            RedirectAttributes redirectAttributes) {
        var user = currentUser.currentUser();
        var application = em.find(Application.class, Integer.valueOf(appId));

        boolean success = purchasingService.purchaseApp(support.getSupport(), application, user);
        if (success) {
            redirectAttributes.addFlashAttribute("purchaseStatus", "success");
        } else {
            redirectAttributes.addFlashAttribute("purchaseStatus", "failure");
        }

        return "redirect:/application/" + appId + "/buy";
    }

}
