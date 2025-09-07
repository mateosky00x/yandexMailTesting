package com.epam.ta.core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Element {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected By locator;

    public Element(WebDriver driver, By locator) {
        this.driver = driver;
        this.locator = locator;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement getElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void click() {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void type(String text) {
        WebElement element = getElement();
        element.clear();
        element.sendKeys(text);
    }

    public String getText() {
        return getElement().getText();
    }

    public boolean isDisplayed() {
        try {
            return getElement().isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
