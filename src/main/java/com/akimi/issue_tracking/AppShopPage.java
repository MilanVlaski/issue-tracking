package com.akimi.issue_tracking;

import com.akimi.issue_tracking.entities.Application;
import com.akimi.issue_tracking.entities.SupportType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.logging.Logger;

@Controller
public class AppShopPage {

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/")
    public String index(Model model) {
        var apps = new ArrayList<Application>(
                em.createQuery("select a from Application a").getResultList()
        );

        model.addAttribute("apps", apps);
        return "index";
    }

    @GetMapping("/application/buy")
    public String buy(Model model, @RequestParam Integer appId) {
        var app = em.find(Application.class, appId);
        var supportTypes = new ArrayList<SupportType>(
                em.createQuery("select s from SupportType s").getResultList()
        );

        model.addAttribute("app", app);
        model.addAttribute("supportTypes", supportTypes);
        return "buy";
    }

    @Autowired
    private PurchasingService purchasingService;

    @PostMapping("/application/buy")
    public String processPurchase(@RequestParam String appId, @ModelAttribute Support support,
            RedirectAttributes redirectAttributes) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("USER NAMED " + email + "CREATING PURCHASE. WITH SUPPORT: " + support.getSupport());
        boolean success = purchasingService.purchaseApp(support.getSupport(), appId, email);
        var appBuyPath = "/application/buy?appId=" + appId;
        if (success) {
            redirectAttributes.addFlashAttribute("purchaseStatus", "success");
        } else {
            redirectAttributes.addFlashAttribute("purchaseStatus", "failure");
        }

        return "redirect:/application/buy?appId=" + appId;

    }


    Logger log = Logger.getLogger(AppShopPage.class.getName());

}

