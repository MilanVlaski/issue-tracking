package com.akimi.issue_tracking.integration;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.ProblemProcessing;
import com.akimi.issue_tracking.problem.dto.ProblemReport;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProblemTest extends BaseIntegrationTest {

    @Autowired
    ProblemProcessing problemProcessing;

    @Given("a problem has been reported on an application with description {string}")
    public void aProblemHasBeenReportedOnAnApplication(String problemDescription) {
        var user = new User("Joe Schmoe", "joe@dot.com", "password",
                LocalDate.of(1995, 10, 10), "Kansas");
        var application = new Application("Appigo", "1.2", "Great.",
                LocalDate.of(2023, 10, 10), "http://wawa.com/img.png");
        var problemReport = new ProblemReport(problemDescription, "Action 1\n Action 2");

        problemProcessing.report(problemReport, application, user);
    }

    @When("an engineer sees a reported problem with description {string}")
    public void an_engineer_sees_a_reported_problem(String problemDescription) {
        driver.get(homepage() + "/engineer/problems");
        inputEngineerEmailAndPassword();
        assertElementContainingTextExists(problemDescription);
    }

    @And("the engineer assigns that problem to themselves")
    public void theEngineerAssignsAProblemToThemselves() {
        click("Assign Problem to Self");
    }

    @Then("they have it in their list of problems with description {string}")
    public void theyHaveItInTheirListOfProblems(String problemDescription) {
        click("My Problems");
        assertElementContainingTextExists(problemDescription);
    }

    private void inputEngineerEmailAndPassword() {
        var email = "john.smith@example.com";
        var password = "password";
        input(email, password);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}