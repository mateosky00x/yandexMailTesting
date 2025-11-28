package com.epam.ta.test;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.factory.PageFactory;
import com.epam.ta.model.User;
import com.epam.ta.page.LoginPage;
import com.epam.ta.page.MailPage;
import com.epam.ta.service.UserCreator;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.WebDriver;

import static org.testng.AssertJUnit.assertTrue;

/**
 * Step definitions for Test_03_Empty_Addressee_Error
 * Scenario: Send email without addressee shows error
 */
public class EmptyAddresseeErrorSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private MailPage mailPage;

    private User testUser = UserCreator.withCredentialsFromProperty();

    private String currentSubject;
    private String currentBody;

    @Before
    public void setUp() {
        driver = DriverSingleton.getDriver();
        loginPage = PageFactory.getPage(LoginPage.class, driver);
        mailPage = PageFactory.getPage(MailPage.class, driver);
    }

    @After
    public void tearDown() {
        DriverSingleton.closeDriver();
    }

    @Given("the user navigates to the mail application")
    public void navigateToMailApplication() {
        loginPage.navigateToMail();
    }

    @When("the user logs in with valid credentials")
    public void loginWithValidCredentials() {
        loginPage.login(testUser.getUsername(), testUser.getPassword());
    }

    @Then("the user should be successfully logged in")
    public void verifySuccessfulLogin() {
        assertTrue("Login failed", loginPage.isUserLoggedIn());
    }

    @When("the user composes an email without recipient with subject {string} and body {string}")
    public void composeEmailWithoutRecipient(String subject, String body) {
        this.currentSubject = subject;
        this.currentBody = body;

        mailPage.composeEmailWithoutRecipient(currentSubject, currentBody);
    }

    @And("the user sends the email")
    public void sendEmail() {
        mailPage.sendEmail();
    }

    @Then("an error popup should be displayed")
    public void verifyErrorPopupDisplayed() {
        assertTrue("Error pop-up not displayed", mailPage.isErrorPopupDisplayed());
    }

    @And("the error message should contain {string}")
    public void verifyErrorMessage(String expectedMessage) {
        assertTrue("Incorrect error message",
                mailPage.getErrorPopupText().contains(expectedMessage));
    }

    @And("the user closes the error popup")
    public void closeErrorPopup() {
        mailPage.closeErrorPopup();
    }

    @And("the user logs out")
    public void logout() {
        mailPage.logout();
    }
}