package com.akimi.issue_tracking;

import com.akimi.issue_tracking.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchasingService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public boolean purchaseApp(String supportTypeId, String appId, String username) {
        var user = em.createQuery("select u from User u where u.name = :username", User.class)
                     .setParameter("username", username)
                     .getSingleResult();
        var application = em.find(Application.class, Integer.valueOf(appId));
        var support = em.find(SupportType.class, supportTypeId);

        var purchase = new Purchase(user, application, support);
        em.persist(purchase);

        return true;
    }
}
