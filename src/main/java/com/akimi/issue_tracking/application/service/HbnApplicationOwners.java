package com.akimi.issue_tracking.application.service;

import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.application.purchase.SupportType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HbnApplicationOwners implements ApplicationOwners {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<UserPurchaseInfo> withApplicationAndMajorVersion(String appName, String appVersion) {
        return em.createQuery(
                        "SELECT new com.akimi.issue_tracking.application.service.UserPurchaseInfo(p.user, p.supportType) " +
                                "FROM Purchase p " +
                                "JOIN p.application app " +
                                "WHERE app.name = :appName " +
                                "AND app.version LIKE :versionPattern", UserPurchaseInfo.class)
                .setParameter("appName", appName)
                .setParameter("versionPattern", majorVersion(appVersion))
                .getResultList();
    }

    private String majorVersion(String oldVersion) {
        return oldVersion.substring(0, oldVersion.lastIndexOf('.')) + ".%";
    }


}

