package com.akimi.issue_tracking.application.service;

import java.util.List;

public interface ApplicationOwners {

    List<UserPurchaseInfo> withApplicationAndMajorVersion(String appName, String appVersion);

}