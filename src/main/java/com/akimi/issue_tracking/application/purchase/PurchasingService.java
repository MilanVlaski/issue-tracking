package com.akimi.issue_tracking.application.purchase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Dependencies that perform real purchasing should go to this class.
 */
@Service
public class PurchasingService {

    @PersistenceContext
    private EntityManager em;

    /**
     * Performs a purchase, initiated by a user, of an application, with a
     * support type.
     *
     * @param supportTypeId should be replaced by the entity.
     * @param application that is being purchased
     * @param user the user purchasing the application
     * @return true if successful, otherwise false
     */
    @Transactional
    public boolean purchaseApp(String supportTypeId, Application application, User user) {
        var supportType = em.find(SupportType.class, supportTypeId);
	    var purchase = user.purchase(application, supportType);
        em.persist(purchase);
        return true;
    }
}
