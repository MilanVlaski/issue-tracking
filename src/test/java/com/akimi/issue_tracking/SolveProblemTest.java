package com.akimi.issue_tracking;

import io.cucumber.java.AfterAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class SolveProblemTest {

    private static final WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless"));

    @Given("a list of applications")
    public void a_list_of_applications() {
        driver.get("localhost:8080/applications");
    }

    @When("the user purchases an application with any tech support")
    public void the_user_purchases_an_application() {
        driver.findElements(By.className("application"))
              .getFirst()
              .click();

//        new Select(driver.findElement(By.name("support")))
//                .selectByIndex(0);
//        driver.findElement(By.tagName("form")).submit();
    }

    @AfterAll
    public static void quitDriver() {
        driver.quit();
    }
}
