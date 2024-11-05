package com.akimi.issue_tracking.selenium_util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SeleniumUtil {

    /**
     * Re
     * @param href link you want to click on
     * @param driver driver used in your tests
     */
    public static void clickLinkByHref(String href, WebDriver driver) {
        List<WebElement> anchors = driver.findElements(By.tagName("a"));

        for (WebElement anchor : anchors) {
            if (anchor.getAttribute("href").contains(href)) {
                anchor.click();
                break;
            }
        }
    }
}
