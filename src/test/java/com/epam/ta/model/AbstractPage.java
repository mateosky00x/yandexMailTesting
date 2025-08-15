package com.epam.ta.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class AbstractPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final int WAIT_TIMEOUT_SECONDS = 10;

    protected AbstractPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
    }

    protected abstract AbstractPage openPage();

    private static final Logger LOGGER = LogManager.getLogger();




}
