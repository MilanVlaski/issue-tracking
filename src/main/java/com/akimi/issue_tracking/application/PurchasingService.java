package com.akimi.issue_tracking.application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchasingService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public boolean purchaseApp(String supportTypeId, Application application, User user) {
        var supportType = em.find(SupportType.class, supportTypeId);
        var purchase = new Purchase(user, application, supportType);
        em.persist(purchase);
        return true;
    }
}
