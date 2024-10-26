package com.akimi.issue_tracking;

import io.cucumber.java.AfterAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class SolveProblemTest {

    @LocalServerPort
    private int port;

    private static final WebDriver driver = new ChromeDriver(
            new ChromeOptions().addArguments("--headless")
    );

    @Given("a list of applications")
    public void a_list_of_applications() {
        driver.get("http://localhost:" + port);
    }

    @When("the user purchases an application with any tech support")
    public void the_user_purchases_an_application() {
        // buy first application
        driver.findElements(By.className("application"))
              .getFirst()
              .findElement(By.tagName("form"))
              .submit();

//        // select the first support type
//        var support = driver.findElement(By.name("support"));
//        new Select(support)
//                .selectByIndex(0);
//        driver.findElement(By.tagName("form")).submit();
//
//        // Submits the form on which support element was, completing the purchase.
//        support.submit();
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
