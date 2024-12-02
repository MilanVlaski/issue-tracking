package com.akimi.issue_tracking.application.service;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.purchase.Purchase;
import com.akimi.issue_tracking.application.purchase.SupportType;
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

    public List<Purchase> sendApplicationToPreviousUsers(Application application, SupportType supportType) {
        var users = applicationOwners.withApplicationAndFeatureRelease(
                application.getName(),
                application.getVersion()
        );
        var purchases = new ArrayList<Purchase>();
        for(User user : users) {
            purchases.add(new Purchase(user, application, supportType));
        }
        return purchases;
    }
}
