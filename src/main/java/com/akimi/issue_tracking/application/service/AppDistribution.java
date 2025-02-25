package com.akimi.issue_tracking.application.service;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.purchase.Purchase;
import com.akimi.issue_tracking.application.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppDistribution {
    private final ApplicationOwners applicationOwners;

    @Autowired
    public AppDistribution(ApplicationOwners applicationOwners) {
        this.applicationOwners = applicationOwners;
    }

    public List<Purchase> sendApplicationToPreviousUsers(Application application) {
        var users = applicationOwners.withApplicationAndMajorVersion(
                application.getName(),
                application.getVersionWithoutPatch()
        );
        var purchases = new ArrayList<Purchase>();
        for (UserPurchaseInfo info : users) {
            purchases.add(new Purchase(info.user(), application, info.supportType()));
        }
        return purchases;
    }
}
