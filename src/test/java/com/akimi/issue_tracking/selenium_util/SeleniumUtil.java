package com.akimi.issue_tracking.selenium_util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;

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
     * Clicks the first link whose href contains the given substring, or regex.
     *
     * @param hrefRegex the regex to search for in the href attribute
     * @param driver the WebDriver used in your tests
     */
    public static void clickLinkByHrefContaining(String hrefRegex, WebDriver driver) {
        List<WebElement> anchors = driver.findElements(By.tagName("a"));
        Pattern pattern = Pattern.compile(hrefRegex);

        for (WebElement anchor : anchors) {
            String href = anchor.getAttribute("href");
            if (href != null) {
                Matcher matcher = pattern.matcher(href);
                if (matcher.find()) {
                    anchor.click();
                    break;
                }
            }
        }
    }

}
