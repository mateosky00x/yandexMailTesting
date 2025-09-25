package com.epam.ta.page;
import com.epam.ta.core.Button;
import com.epam.ta.core.TextBox;
import com.epam.ta.core.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    // LoginPage specific locators
    private static final By LOGIN_BUTTON = By.cssSelector("a[href*='auth']");
    private static final By USERNAME_FIELD = By.id("passp-field-login");
    private static final By PASSWORD_FIELD = By.id("passp-field-passwd");
    private static final By SUBMIT_BUTTON = By.cssSelector("button[type='submit']");
    private static final By NEXT_BUTTON = By.cssSelector("button[data-t='button:action:passp:sign-in']");
    private static final By INTERFACE_LOADED = By.className("UserID-name");

    private Button loginButton;
    private TextBox usernameField;
    private Button submitButton;
    private TextBox passwordField;
    private Button nextButton;
    private Label interfaceLoaded;

    public LoginPage(WebDriver driver) {
        super(driver);
        logger.info("Initializing LoginPage");
        this.loginButton = new Button(driver, LOGIN_BUTTON);
        this.usernameField = new TextBox(driver, USERNAME_FIELD);
        this.submitButton = new Button(driver, SUBMIT_BUTTON);
        this.passwordField = new TextBox(driver, PASSWORD_FIELD);
        this.nextButton = new Button(driver, NEXT_BUTTON);
        this.interfaceLoaded = new Label(driver, INTERFACE_LOADED);
        logger.debug("LoginPage elements initialized successfully");
    }

    @Override
    protected BasePage openPage() {
        logger.info("Opening Yandex Mail page");
        driver.get("https://360.yandex.com/mail/");
        logger.debug("Successfully navigated to Yandex Mail");
        return this;
    }

    public void navigateToMail() {
        logger.info("Navigating to Yandex Mail");
        driver.get("https://360.yandex.com/mail/");
        logger.debug("Navigation completed");
    }

    public void login(String username, String password) {
        logger.info("Starting login process for user: {}", username);
        try {
            loginButton.click();
            logger.debug("Login button clicked");

            usernameField.typeText(username);
            logger.debug("Username entered");

            submitButton.click();
            logger.debug("Submit button clicked");

            passwordField.typeText(password);
            logger.debug("Password entered");

            nextButton.click();
            logger.debug("Next button clicked");

            interfaceLoaded.isDisplayed();
            logger.info("Login completed successfully for user: {}", username);
        } catch (Exception e) {
            logger.error("Login failed for user: {}", username, e);
            throw e;
        }
    }

    public boolean isUserLoggedIn() {
        boolean loggedIn = interfaceLoaded.isDisplayed();
        logger.debug("User login status: {}", loggedIn);
        return loggedIn;
    }
}