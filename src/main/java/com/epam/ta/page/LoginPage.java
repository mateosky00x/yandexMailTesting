package com.epam.ta.page;
import com.epam.ta.model.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends AbstractPage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected AbstractPage openPage() {
        driver.get("https://360.yandex.com/mail/");
        return this;
    }

    public void navigateToMail() {
        driver.get("https://360.yandex.com/mail/");
    }

    public void login(String username, String password) {
        click(Locators.LOGIN_BUTTON);
        sendKeys(Locators.USERNAME_FIELD, username);
        click(Locators.SUBMIT_BUTTON);
        sendKeys(Locators.PASSWORD_FIELD, password);
        click(Locators.NEXT_BUTTON);
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.INTERFACE_LOADED));
    }

    public boolean isUserLoggedIn() {
        return isDisplayed(Locators.INTERFACE_LOADED);
    }

    private void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void sendKeys(By locator, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
    }

    private boolean isDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
