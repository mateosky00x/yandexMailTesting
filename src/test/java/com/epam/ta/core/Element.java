package com.epam.ta.core;


import org.openqa.selenium.By;

public abstract class Element {
    public abstract By getBy();
    public abstract String getText();
    public abstract void click();
    public abstract void typeText(String text);
    public abstract boolean isDisplayed();
}
