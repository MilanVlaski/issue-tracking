package com.akimi.issue_tracking;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver(
                new ChromeOptions().addArguments("--headless")
        );
    }

    @Test
    void login_with_credentials_works_if_same_as_the_ones_registered_with() {
        var email = "john@doe.com";
        var password = "somePassword";
        registerWithEmailAndPassword(email, password);
        login(email, password);
        assertEquals("http://localhost:" + port + "/", driver.getCurrentUrl());
    }

    @Test
    void login_with_credentials_does_not_work_if_different_than_the_ones_registered_with() {
        var email = "john@doe.com";
        registerWithEmailAndPassword(email, "somePassword");
        login(email, "different-pass");
        assertEquals("http://localhost:" + port + "/login?error",
                driver.getCurrentUrl());
    }

    private void registerWithEmailAndPassword(String email, String registeredPassword) {
        driver.get("http://localhost:" + port + "/login");
        // go to register, since we don't have an account
        new WebDriverWait(driver, Duration.ofMillis(500))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[href=\"/register\"]"))).click();
        // enter user details
        driver.findElement(By.name("name")).sendKeys("John Doe");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(registeredPassword);
        // optional
        driver.findElement(By.name("birthYear")).sendKeys("2020-10-10");
        driver.findElement(By.name("phoneNumber")).sendKeys("387231231");
        var location = driver.findElement(By.name("location"));
        location.sendKeys("New York");

        location.submit();
    }

    void login(String email, String password) {
        driver.findElement(By.name("email")).sendKeys(email);
        var pass = driver.findElement(By.name("password"));
        pass.sendKeys(password);
        pass.submit();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}
