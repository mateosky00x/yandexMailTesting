package com.epam.ta.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebCoreElement extends Element {

    protected final WebDriver webDriver;
    protected final By by;

    // It saves driver and locator
    public WebCoreElement(WebDriver webDriver, By by) {
        this.webDriver = webDriver;
        this.by = by;
    }

    // Looks dinamically webdriver when used
    protected WebElement getWebElement() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    @Override
    public By getBy() {
        return by;
    }

    @Override
    public String getText() {
        return getWebElement().getText();
    }

    @Override
    public void click() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(by));
        getWebElement().click();
    }

    @Override
    public void typeText(String text) {
        WebElement element = getWebElement();
        element.clear();
        element.sendKeys(text);
    }

    @Override
    public boolean isDisplayed() {
        try {
            return getWebElement().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
