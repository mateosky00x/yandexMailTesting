package com.epam.ta.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Button extends Element {
    public Button(WebDriver driver, By locator) {
        super(driver, locator);
    }
}

