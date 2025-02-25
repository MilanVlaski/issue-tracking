package com.akimi.issue_tracking.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.purchase.Purchase;

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
		application.getMajorVersion() // should be get major version instead
        );
        var purchases = new ArrayList<Purchase>();
        for (UserPurchaseInfo info : users) {
            purchases.add(new Purchase(info.user(), application, info.supportType()));
        }
        return purchases;
    }
}
