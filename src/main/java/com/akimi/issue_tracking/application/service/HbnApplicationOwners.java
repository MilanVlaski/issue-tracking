package com.akimi.issue_tracking.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
                .setParameter("versionPattern", appVersion + ".%")
                .getResultList();
    }

}

