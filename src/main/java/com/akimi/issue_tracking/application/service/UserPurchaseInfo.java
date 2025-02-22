package com.akimi.issue_tracking.application.service;

import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.application.purchase.SupportType;

public record UserPurchaseInfo(
        User user,
        SupportType supportType) {
}
