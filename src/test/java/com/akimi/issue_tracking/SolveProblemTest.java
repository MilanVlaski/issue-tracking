package com.akimi.issue_tracking;

import io.cucumber.java.AfterAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class SolveProblemTest {

    @LocalServerPort
    private int port;

    int appId = 1;

    private String homepage() {
        return "http://localhost:" + port;
    }

    @Given("a list of applications")
    public void a_list_of_applications() {
        driver.get(homepage());
    }


    @When("the user purchases an application with any tech support")
    public void the_user_purchases_an_application() {
        // buy first app
        clickLinkLeadingTo("/application/" + appId + "/buy");

        // we are not signed in, so we get taken to a sign-in page
        inputEmailAndPassword();

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

        var action1 = "I log in using my email and password.";
        var action2 = "I get an error message saying \"Something went wrong.\".";
        var actions = driver.findElement(By.name("actions"));
        actions.sendKeys(String.join("\n", action1, action2));
        actions.submit();
    }

    private void inputEmailAndPassword() {
        driver.findElement(By.name("email")).sendKeys("john.doe@example.com");
        var passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys("password");
        passwordElement.submit();
    }

    private static final WebDriver driver = new ChromeDriver(
            new ChromeOptions().addArguments("--headless")
    );

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    public void clickLinkLeadingTo(String href) {
        driver.get(homepage() + href);
    }
}
