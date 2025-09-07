package com.epam.ta.test;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.factory.PageFactory;
import com.epam.ta.model.AbstractPage;
import com.epam.ta.util.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({TestListener.class})
public class CommonConditions {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverSingleton.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void stopBrowser() {
        DriverSingleton.closeDriver();
    }

    // ✅ Factory method to get pages
    protected <T extends AbstractPage> T getPage(Class<T> pageClass) {
        return PageFactory.getPage(pageClass, driver);
    }
}
