package com.epam.ta.page;

import com.epam.ta.model.AbstractPage;
import com.epam.ta.core.Button;
import com.epam.ta.core.TextBox;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends AbstractPage {

    private Button loginButton;
    private TextBox usernameField;
    private Button submitButton;
    private TextBox passwordField;
    private Button nextButton;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.loginButton = new Button(driver, Locators.LOGIN_BUTTON);
        this.usernameField = new TextBox(driver, Locators.USERNAME_FIELD);
        this.submitButton = new Button(driver, Locators.SUBMIT_BUTTON);
        this.passwordField = new TextBox(driver, Locators.PASSWORD_FIELD);
        this.nextButton = new Button(driver, Locators.NEXT_BUTTON);
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
        loginButton.click();
        usernameField.enterText(username);
        submitButton.click();
        passwordField.enterText(password);
        nextButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.INTERFACE_LOADED));
    }

    public boolean isUserLoggedIn() {
        return driver.findElement(Locators.INTERFACE_LOADED).isDisplayed();
    }
}

