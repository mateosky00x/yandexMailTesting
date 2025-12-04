package com.epam.ta.steps;

import com.epam.ta.driver.DriverSingleton;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private WebDriver driver;

    @Before
    public void setUp() {
        // Initializes WebDriver once per scenario
        driver = DriverSingleton.getDriver();
    }

    @After
    public void tearDown() {
        // Always quit after each scenario
        DriverSingleton.closeDriver();
    }
}

