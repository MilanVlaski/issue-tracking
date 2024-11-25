package com.akimi.issue_tracking;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.ProblemProcessing;
import com.akimi.issue_tracking.problem.ProblemReport;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Disabled;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProblemTest {

    @LocalServerPort
    private int port;

    private String homepage() {
        return "http://localhost:" + port;
    }

    String problemDescription = "Problem description.";

    @Autowired
    ProblemProcessing problemProcessing;

    @Given("a problem has been reported on an application")
    public void aProblemHasBeenReportedOnAnApplication() {
        var user = new User("Joe Schmoe", "joe@dot.com", "password",
                LocalDate.of(1995, 10, 10), "Kansas");
        var application = new Application("Appigo", "1.2", "Great.",
                LocalDate.of(2023, 10, 10), "http://wawa.com/img.png");
        var problemReport = new ProblemReport(problemDescription, "Action 1\n Action 2");

        problemProcessing.report(problemReport, application, user);
    }

    @When("an engineer sees a reported problem")
    public void an_engineer_sees_a_reported_problem() {
        driver.get(homepage() + "/engineer/problems");
        wait.until(visibilityOf(driver.findElement(By.xpath(
                "//*[contains(text(), '" + problemDescription + "')]"))));
    }

    private static final WebDriver driver = new ChromeDriver(
            new ChromeOptions().addArguments("--headless")
    );

    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(100));

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}

