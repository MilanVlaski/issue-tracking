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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SolveProblemTest extends BaseIntegrationTest {

    @LocalServerPort
    private int port;

    String action1 = "I log in using my email and password.";
    String action2 = "I get an error message saying \"Something went wrong.\".";
    String answer = "It looks like your password had a space in it." +
            " Please change your password, and that should solve things!";

    @Given("a list of applications")
    public void a_list_of_applications() {
        driver.get(homepage(port));
    }

    @When("the user purchases an application with any tech support")
    public void the_user_purchases_an_application() {
        click("Buy Application");
        inputUserEmailAndPassword();
        var support = driver.findElement(By.name("support"));
        new Select(support)
                .selectByIndex(0);

        support.submit();
    }

    @Then("the user is able to file a problem report with description {string}")
    public void theUserIsAbleToFileAProblemReportOnThatApplication(String problemDescription) {
        click("Report a Problem");
        click("Report a Problem With the Application");
        driver.findElement(By.name("description"))
              .sendKeys(problemDescription);

        var actions = driver.findElement(By.name("actions"));
        actions.sendKeys(String.join("\n", action1, action2));
        actions.submit();
        click("Log Out");
    }

    @When("an engineer sees a reported problem with description {string}")
    public void an_engineer_sees_a_reported_problem(String problemDescription) {
        driver.get(homepage(port) + "/engineer/problems");
        inputEngineerEmailAndPassword();
        assertElementContainingTextExists(problemDescription);
    }

    @And("an engineer can post an answer to the problem")
    public void anEngineerCanPostAnAnswerToTheProblem() {
        driver.get(homepage(port) + "/engineer/problems");
        inputEngineerEmailAndPassword();

        click("View Problem");
        assertThatActionsAppearInSameOrderAsDescribed();
        driver.findElement(By.name("answer"))
              .sendKeys(answer);
        click("Answer Problem");
    }

    @Then("the user can look at the answer and be happy")
    public void theUserCanLookAtTheAnswerAndBeHappy() {
        click("Log Out");
        driver.get(homepage(port));
        click("Log In");
        inputUserEmailAndPassword();

        click("See Reported Problems");
        click("See Fixes");

        assertElementContainingTextExists(answer);
    }

    @And("the engineer assigns this problem to themselves")
    public void theEngineerAssignsThisProblemToThemselves() {
        click("Assign Problem to Self");
    }

    @When("the engineer patches the problem")
    public void theEngineerPatchesTheProblem() {
        click("My Problems");
        click("Upload a Patch");
        driver.findElement(By.name("size"))
              .sendKeys("500");
        var communicationType = driver.findElement(By.name("communicationType"));
        communicationType.sendKeys("Phone call");
        communicationType.submit();
        assertElementContainingTextExists("Successfully uploaded");
    }

    @Then("the user can install the patch")
    public void theUserCanInstallThePatch() {
        click("Log Out");
        driver.get(homepage(port));
        click("Log In");
        inputUserEmailAndPassword();

        click("See Reported Problems");
        click("See Fixes");

        click("Install Patch");
        click("Install Application");
        assertElementContainingTextExists("Your application will be installed shortly.");
    }

    private void assertThatActionsAppearInSameOrderAsDescribed() {
        WebElement firstElement = driver.findElement(By.cssSelector("ol > li:first-child"));
        WebElement secondElement = driver.findElement(By.cssSelector("ol > li:nth-child(2)"));

        assertEquals(action1, firstElement.getText());
        assertEquals(action2, secondElement.getText());
    }

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

    @And("the engineer assigns that problem to themselves")
    public void theEngineerAssignsAProblemToThemselves() {
        click("Assign Problem to Self");
    }

    @Then("they have it in their list of problems with description {string}")
    public void theyHaveItInTheirListOfProblems(String problemDescription) {
        click("My Problems");
        assertElementContainingTextExists(problemDescription);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
