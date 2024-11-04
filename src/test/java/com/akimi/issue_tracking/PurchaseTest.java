package com.akimi.issue_tracking;

import com.akimi.issue_tracking.entities.Application;
import com.akimi.issue_tracking.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class PurchaseTest {

    @Autowired
    TestEntityManager tem;

    @Autowired
    PurchasingService purchasingService;

    @Test
    public void testPurchase() {
        var username = "Jane Doe";
        var appName = "Best app";
        var supportId = "BEZ_POD"; // this is in the database by default

        var user = new User().setName(username).setEmail("m@v.com");
        var app = new Application().setName(appName).setVersion("1.1");
        var userId = tem.persistAndGetId(user);
        var appId = tem.persistAndGetId(app);

        purchasingService.purchaseApp(supportId, appId.toString(), username);

        assertEquals(appName, tem.find(User.class, userId).getPurchases().iterator().next()
                                 .getApplication().getName());
    }

    @Test
    public void testPurchase2() {
        var username = "Jane Doe";
        var appName = "Best app";
        var supportId = "BEZ_POD"; // this is in the database by default

        var user = new User().setName(username).setEmail("m@v.com");
        var app = new Application().setName(appName).setVersion("1.1");
        var userId = tem.persistAndGetId(user);
        var appId = tem.persistAndGetId(app);

        purchasingService.purchaseApp(supportId, appId.toString(), username);

        assertEquals(appName, tem.find(User.class, userId).getPurchases().iterator().next()
                                 .getApplication().getName());
    }

}
