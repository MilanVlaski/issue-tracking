package com.akimi.issue_tracking.integration;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SolveProblemTest extends BaseIntegrationTest {

    String action1 = "I log in using my email and password.";
    String action2 = "I get an error message saying \"Something went wrong.\".";
    String answer = "It looks like your password had a space in it." +
            " Please change your password, and that should solve things!";

    @Given("a list of applications")
    public void a_list_of_applications() {
        driver.get(homepage());
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
    }

    @And("an engineer can post an answer to the problem")
    public void anEngineerCanPostAnAnswerToTheProblem() {
        click("Log Out");
        driver.get(homepage() + "/engineer/problems");
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
        driver.get(homepage());
        click("Log In");
        inputUserEmailAndPassword();

        click("See Reported Problems");
        click("See Fixes");

        assertElementContainingTextExists(answer);
    }

    @When("the engineer patches the problem")
    public void theEngineerPatchesTheProblem() {
        click("My Problems");
        click("Upload a Patch");
        // We get a display of the problem, and the application,
        // and a form with size and communication type, (don't forget the publish date)
        // Get a message saying "Successfully uploaded {app.name} {app.version}"
    }

    private void assertThatActionsAppearInSameOrderAsDescribed() {
        WebElement firstElement = driver.findElement(By.cssSelector("ol > li:first-child"));
        WebElement secondElement = driver.findElement(By.cssSelector("ol > li:nth-child(2)"));

        assertEquals(action1, firstElement.getText());
        assertEquals(action2, secondElement.getText());
    }

    public void click(String s) {
        wait.until(elementToBeClickable(
                By.cssSelector("[aria-label='" + s + "']"))
        ).click();
    }

    private void inputUserEmailAndPassword() {
        var email = "john.doe@example.com";
        var password = "password";
        input(email, password);
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
