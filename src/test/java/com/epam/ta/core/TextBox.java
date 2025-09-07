package com.epam.ta.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TextBox extends Element {
    public TextBox(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public void enterText(String text) {
        type(text);
    }
}
