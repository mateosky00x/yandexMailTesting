package com.epam.ta.page;

import com.epam.ta.core.Button;
import com.epam.ta.core.TextBox;
import com.epam.ta.core.Label;
import com.epam.ta.model.AbstractPage;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {

    private Button loginButton;
    private TextBox usernameField;
    private Button submitButton;
    private TextBox passwordField;
    private Button nextButton;
    private Label interfaceLoaded;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.loginButton = new Button(driver, Locators.LOGIN_BUTTON);
        this.usernameField = new TextBox(driver, Locators.USERNAME_FIELD);
        this.submitButton = new Button(driver, Locators.SUBMIT_BUTTON);
        this.passwordField = new TextBox(driver, Locators.PASSWORD_FIELD);
        this.nextButton = new Button(driver, Locators.NEXT_BUTTON);
        this.interfaceLoaded = new Label(driver, Locators.INTERFACE_LOADED);
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
        usernameField.typeText(username);
        submitButton.click();
        passwordField.typeText(password);
        nextButton.click();
        interfaceLoaded.isDisplayed();
    }

    public boolean isUserLoggedIn() {
        return interfaceLoaded.isDisplayed();
    }
}
