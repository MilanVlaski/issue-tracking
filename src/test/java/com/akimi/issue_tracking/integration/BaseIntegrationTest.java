package com.akimi.issue_tracking.integration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public abstract class BaseIntegrationTest {

    @LocalServerPort
    protected int port;

    protected final WebDriver driver = new ChromeDriver(
            new ChromeOptions().addArguments("--headless")
    );

    protected final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(100));

    protected String homepage() {
        return "http://localhost:" + port;
    }

    protected void click(String label) {
        wait.until(elementToBeClickable(By.cssSelector("[aria-label='" + label + "']"))).click();
    }


    protected void input(String email, String password) {
        driver.findElement(By.name("email")).sendKeys(email);
        var passwordElement = driver.findElement(By.name(password));
        passwordElement.sendKeys("password");
        passwordElement.submit();
    }

    protected void assertElementContainingTextExists(String text) {
        driver.findElement(By.xpath(
                "//*[contains(text(), '" + text + "')]"));
    }
}