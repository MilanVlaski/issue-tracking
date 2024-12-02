package com.akimi.issue_tracking.application.service;

import com.akimi.issue_tracking.application.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationOwners {

    @PersistenceContext
    private EntityManager em;

    public List<User> withApplicationAndFeatureRelease(String appName, String appVersion) {
        return em.createQuery(
                         "SELECT DISTINCT p.user " +
                                 "FROM Purchase p " +
                                 "JOIN p.application app " +
                                 "WHERE app.name = :appName " +
                                 "AND app.version LIKE :versionPattern", User.class)
                 .setParameter("appName", appName)
                 .setParameter("versionPattern", featureReleaseVersion(appVersion))
                 .getResultList();
    }

    private String featureReleaseVersion(String oldVersion) {
        return oldVersion.substring(0, oldVersion.lastIndexOf('.')) + ".%";
    }
}
