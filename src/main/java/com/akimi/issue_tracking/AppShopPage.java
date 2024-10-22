package com.akimi.issue_tracking;

import com.akimi.issue_tracking.entities.Application;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppShopPage {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private final List<Application> applications = List.of(
            new Application("Dev Dreams", "1.2", "Develop web apps faster!", LocalDate.of(2020, 1, 1), "https://st3.depositphotos.com/43745012/44906/i/450/depositphotos_449066958-stock-photo-financial-accounting-logo-financial-logo.jpg"),
            new Application("Code Sprint", "2.0", "Speed up your coding projects!", LocalDate.of(2021, 5, 15), "https://png.pngtree.com/png-vector/20220509/ourmid/pngtree-company-logo-design-trademark-design-creative-logo-png-image_4569380.png"),
            new Application("Design Studio", "3.5", "Create stunning designs easily!", LocalDate.of(2019, 8, 23), "https://st5.depositphotos.com/1029305/68399/i/380/depositphotos_683998616-stock-photo-gold-abstract-hexagon-shaped-optical.jpg"),
            new Application("TaskMaster", "4.1", "Manage your tasks efficiently!", LocalDate.of(2022, 3, 10), "https://static8.depositphotos.com/1378583/1010/i/380/depositphotos_10106840-stock-photo-face-logo.jpg"),
            new Application("Insight Pro", "1.8", "Get deep insights from your data!", LocalDate.of(2020, 11, 5), "https://www.shutterstock.com/image-vector/circle-line-simple-design-logo-600nw-2174926871.jpg"),
            new Application("Market Pulse", "2.9", "Track market trends in real-time!", LocalDate.of(2018, 7, 30), "https://www.logodesign.net/logo/line-art-buildings-in-swoosh-1273ld.png?size=2"));

    @GetMapping("/")
    public String index(Model model) {
        persistApps();

        var apps = new ArrayList<Application>(
                em.createQuery("select a from Application a").getResultList());

        model.addAttribute("apps", apps);
        return "index";
    }

    void persistApps() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        applications.forEach(em::persist);
        em.getTransaction().commit();
    }

}
