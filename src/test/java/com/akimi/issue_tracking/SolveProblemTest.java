package com.akimi.issue_tracking;

import io.cucumber.java.AfterAll;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SolveProblemTest {

    private final WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless"));

    @Given("a list of applications")
    public void a_list_of_applications() {
        driver.get("localhost:8080/applications");
    }

    @AfterAll
    public void quitDriver() {
        driver.quit();
    }
}
