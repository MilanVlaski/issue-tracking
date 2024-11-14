package com.akimi.issue_tracking;

import com.akimi.issue_tracking.application.PurchasingService;
import com.akimi.issue_tracking.entities.Application;
import com.akimi.issue_tracking.entities.Problem;
import com.akimi.issue_tracking.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class PurchaseTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    PurchasingService purchasingService;

    @Test
    public void testPurchase() {
        var appName = "Best app";
        var supportId = "BEZ_POD"; // this is in the database by default

        var email = "m@v.com";
        var user = new User().setName("Jane Doe").setEmail(email);
        var app = new Application().setName(appName).setVersion("1.1");
        var userId = em.persistAndGetId(user);
        var appId = em.persistAndGetId(app);

        purchasingService.purchaseApp(supportId, appId.toString(), email);

        assertEquals(appName, em.find(User.class, userId).getPurchases().iterator().next()
                                .getApplication().getName());
    }

}
