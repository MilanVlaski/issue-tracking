package com.akimi.issue_tracking;

import com.akimi.issue_tracking.entities.Application;
import com.akimi.issue_tracking.entities.SupportType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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

    @RequestMapping("/application/buy")
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

    @PostMapping("/application/processPurchase")
    public String processPurchase(String appId,
            @ModelAttribute String supportTypeId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean success = purchasingService.purchaseApp(supportTypeId, appId, username);
        var appBuyPath = "/application/buy?appId=" + appId;
        if (success) {
            return appBuyPath + "&success";
        } else {
            return appBuyPath + "&error";
        }
    }

}
