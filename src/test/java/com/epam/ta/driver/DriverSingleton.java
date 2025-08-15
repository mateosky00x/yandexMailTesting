package com.epam.ta.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverSingleton {

    private static WebDriver driver;


    private DriverSingleton() {}

    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = System.getProperty("browser");
            if (browser == null) {
                browser = "chrome";
            }

            switch (browser) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "chrome":
                default:
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--remote-allow-origins=*");
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(options);
                    break;

            }

            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
