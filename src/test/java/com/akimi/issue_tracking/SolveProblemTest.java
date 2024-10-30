package com.akimi.issue_tracking;

import io.cucumber.java.AfterAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
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
        // buy first app
        driver.findElements(By.className("application"))
              .getFirst()
              .findElement(By.cssSelector("[aria-label=\"Buy Application\"]"))
              .click();

        // we are not signed in, so we get taken to a sign-in page
        signIn();

        // select the first support type
        var support = driver.findElement(By.name("support"));
        new Select(support)
                .selectByIndex(0);

        support.submit();
    }

    private void signIn() {
        // todo: get rid of duplication
        inputEmailAndPassword();
//        // optional
//        driver.findElement(By.name("birthYear")).sendKeys("2020-10-10");
//        driver.findElement(By.name("phoneNumber")).sendKeys("387231231");
//        driver.findElement(By.name("location")).sendKeys("New York");
//
    }

    private void inputEmailAndPassword() {
        driver.findElement(By.name("email")).sendKeys("john.doe@example.com");
        var passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys("password");
        passwordElement.submit();
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
