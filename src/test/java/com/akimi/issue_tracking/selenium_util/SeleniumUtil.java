package com.akimi.issue_tracking.selenium_util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SeleniumUtil {

    /**
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


    /**
     * Clicks the first link whose href contains the given substring.
     *
     * @param hrefSubstring the substring to search for in the href attribute
     * @param driver the WebDriver used in your tests
     */
    public static void clickLinkByHrefContaining(String hrefSubstring, WebDriver driver) {
        List<WebElement> anchors = driver.findElements(By.tagName("a"));

        for (WebElement anchor : anchors) {
            String href = anchor.getAttribute("href");
            if (href != null && href.contains(hrefSubstring)) {
                anchor.click();
                break;
            }
        }
    }
}
