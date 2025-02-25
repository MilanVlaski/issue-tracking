package com.akimi.issue_tracking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.application.purchase.PurchasingService;

@SpringBootTest
@Transactional
public class PurchaseTest {

    @Autowired
    PurchasingService purchasingService;

    @Test
    public void testPurchase() {
        var appName = "Best app";

        var supportId = "NO_SUPP"; // this is in the database by default

        var email = "m@v.com";
        var user = new User().setName("Jane Doe").setEmail(email);
	var app = new Application().setName(appName).setVersion("1.1.0");

        purchasingService.purchaseApp(supportId, app, user);

        assertEquals(appName, user.getPurchases().iterator().next()
                                .getApplication().getName());
    }

}
