package com.akimi.issue_tracking;

import io.cucumber.java.AfterAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_scouse.An;
import io.cucumber.spring.CucumberContextConfiguration;
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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class SolveProblemTest {

    @LocalServerPort
    private int port;

    int appId = 1;
    int problemId = 1;
    String action1 = "I log in using my email and password.";
    String action2 = "I get an error message saying \"Something went wrong.\".";


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
        clickLinkLeadingTo("/application/" + appId + "/buy");

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
        clickLinkLeadingTo("/reportProblem");

        var reportProblemOnApplication_Path = "/application/" + appId + "/reportProblem";
        clickLinkLeadingTo(reportProblemOnApplication_Path);
        // get taken to a form where you put in Problem(description, Actions(number, description))
        driver.findElement(By.name("description"))
              .sendKeys("User cannot access their account at login.");

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
        clickLinkLeadingTo("/engineer/problems/" + problemId);
        // should find actions that the user has posted previously
        driver.findElement(By.xpath("//*[text()='" + action1 + "']"));
        // give an answer
        driver.findElement(By.name("answer"))
              .sendKeys("It looks like your password had a space in it." +
                      " Please change your password, and that should solve things!");
        driver.findElement(By.cssSelector("[aria-label=\"Answer Problem\"]"))
              .submit();
    }

    @Then("the user can look at the answer and be happy")
    public void theUserCanLookAtTheAnswerAndBeHappy() {
        // logout, then log the user back in, on the problems page
        logout();
        driver.get(homepage());
        wait.until(elementToBeClickable(By.cssSelector("[aria-label='Log In']"))).click();
//        inputUserEmailAndPassword();
        // go to /problems

        // see your problem answered, nice and green (assertion)
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


    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(100));

    public void clickLinkLeadingTo(String href) {
        wait.until(elementToBeClickable(
                By.cssSelector("[href=\"" + href + "\"]"))
        ).click();
    }

    private static final WebDriver driver = new ChromeDriver(
            new ChromeOptions().addArguments("--headless")
    );

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

}
