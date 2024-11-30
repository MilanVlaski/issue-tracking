package com.akimi.issue_tracking.problem;

import com.akimi.issue_tracking.application.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationOwners {

    @PersistenceContext
    private EntityManager em;

    public List<User> withApplicationAndFeatureRelease(String appName, String oldVersion) {
        // Truncate the version to major.minor pattern
        String featureReleaseVersion = oldVersion.substring(0, oldVersion.lastIndexOf('.')) + ".%";

        return em.createQuery(
                         "SELECT DISTINCT p.user " +
                                 "FROM Purchase p " +
                                 "JOIN p.application app " +
                                 "WHERE app.name = :appName " +
                                 "AND app.version LIKE :versionPattern", User.class)
                 .setParameter("appName", appName)
                 .setParameter("versionPattern", featureReleaseVersion)
                 .getResultList();
    }
}
