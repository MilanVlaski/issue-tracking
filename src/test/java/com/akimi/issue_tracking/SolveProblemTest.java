package com.akimi.issue_tracking;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_scouse.An;
import io.cucumber.spring.CucumberContextConfiguration;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SolveProblemTest {

    @LocalServerPort
    private int port;

    int appId = 1;
    int problemId = 1;
    String action1 = "I log in using my email and password.";
    String action2 = "I get an error message saying \"Something went wrong.\".";
    String problemDescription = "User cannot access their account at login.";
    String answer = "It looks like your password had a space in it." +
            " Please change your password, and that should solve things!";

    @Given("a list of applications")
    public void a_list_of_applications() {
        driver.get(homepage());
    }

    private String homepage() {
        return "http://localhost:" + port;
    }

    @When("the user purchases an application with any tech support")
    public void the_user_purchases_an_application() {
        // buy first app
        click("Buy Application");

        // we are not signed in, so we get taken to a sign-in page
        inputUserEmailAndPassword();

        // select the first support type
        var support = driver.findElement(By.name("support"));
        new Select(support)
                .selectByIndex(0);

        support.submit();
    }

    @Then("the user is able to file a problem report on that application")
    public void theUserIsAbleToFileAProblemReportOnThatApplication() {
        click("Report a Problem");

        click("Report a Problem With the Application");
        // get taken to a form where you put in Problem(description, Actions(number, description))
        driver.findElement(By.name("description"))
              .sendKeys(problemDescription);

        var actions = driver.findElement(By.name("actions"));
        actions.sendKeys(String.join("\n", action1, action2));
        actions.submit();
    }

    @And("an engineer can post an answer to the problem")
    public void anEngineerCanPostAnAnswerToTheProblem() {
        // logout user
        logout();
        // go to /support/problems
        driver.get(homepage() + "/engineer/problems");
        // login engineer
        inputEngineerEmailAndPassword();
        // click on "Solve Problem"
        click("View Problem");
        // should find actions that the user has posted previously, in order
        actionsAppearInSameOrderAsDescribed();
        // give an answer
        driver.findElement(By.name("answer"))
              .sendKeys(answer);
        driver.findElement(By.cssSelector("[aria-label=\"Answer Problem\"]"))
              .submit();
    }

    @Then("the user can look at the answer and be happy")
    public void theUserCanLookAtTheAnswerAndBeHappy() {
        // logout, then log the user back in, on the problems page
        logout();
        driver.get(homepage());
        click("Log In");
        inputUserEmailAndPassword();
        // go to /problems
        click("See Reported Problems");
        click("See Fixes");
        // see your problem answered
        driver.findElement(By.xpath("//*[text()='" + problemDescription + "']"));
        wait.until(visibilityOf(driver.findElement(By.xpath(
                "//*[contains(text(), '" + problemDescription + "')]"))));
        // see problem as resolved
    }

    private void actionsAppearInSameOrderAsDescribed() {
        WebElement firstElement = driver.findElement(By.cssSelector("ol > li:first-child"));
        WebElement secondElement = driver.findElement(By.cssSelector("ol > li:nth-child(2)"));

        assertEquals(action1, firstElement.getText());
        assertEquals(action2, secondElement.getText());
    }

    private void click(String s) {
        wait.until(elementToBeClickable(By.cssSelector("[aria-label='" + s + "']"))).click();
    }

    private void logout() {
        wait.until(
                elementToBeClickable(
                        By.cssSelector("[aria-label='Log Out']"))
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

    private void input(String email, String password) {
        driver.findElement(By.name("email")).sendKeys(email);
        var passwordElement = driver.findElement(By.name(password));
        passwordElement.sendKeys("password");
        passwordElement.submit();
    }


    public void clickLinkLeadingTo(String href) {
        wait.until(elementToBeClickable(
                By.cssSelector("[href=\"" + href + "\"]"))
        ).click();
    }


    private final WebDriver driver = new ChromeDriver(
            new ChromeOptions().addArguments("--headless")
    );

    @After
    public void tearDown() {
        driver.quit();
    }

    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(100));

}
