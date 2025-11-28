package com.epam.ta.test.steps;
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

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Step definitions for Test_02_Delete_Draft
 * Scenario: Delete draft email & Delete multiple drafts
 */
public class DeleteDraftSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private MailPage mailPage;
    private User testUser;

    private String currentRecipient;
    private String currentSubject;
    private String currentBody;

    @Before
    public void setUp() {
        driver = DriverSingleton.getDriver();
        loginPage = PageFactory.getPage(LoginPage.class, driver);
        mailPage = PageFactory.getPage(MailPage.class, driver);
        testUser = UserCreator.withCredentialsFromProperty();
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

    @When("the user composes an email with recipient {string} and subject {string} and body {string}")
    public void composeEmail(String recipient, String subject, String body) {
        this.currentRecipient = recipient;
        this.currentSubject = subject + "-" + System.currentTimeMillis();
        this.currentBody = body;

        mailPage.composeEmail(currentRecipient, currentSubject, currentBody);
    }

    @And("the user saves the email as draft")
    public void saveAsDraft() {
        mailPage.saveAsDraft();
    }

    @And("the user opens the drafts folder")
    public void openDraftsFolder() {
        mailPage.openDraftsFolder();
    }

    @Then("the draft with subject {string} should be visible")
    public void verifyDraftVisible(String subject) {
        assertTrue("Draft not found", mailPage.isEmailInDrafts(currentSubject));
    }

    @When("the user opens the draft message with subject {string}")
    public void openDraftMessage(String subject) {
        mailPage.openDraftMessage(currentSubject);
    }

    @And("the user opens the draft email with subject {string}")
    public void openDraftEmail(String subject) {
        mailPage.openDraftEmail(currentSubject);
    }

    @And("the user deletes the draft")
    public void deleteDraft() {
        mailPage.deleteDraft();
    }

    @Then("the draft should not exist after deletion")
    public void verifyDraftDeletedCompletely() {
        assertFalse("Draft still exists after deletion", mailPage.isEmailInDrafts(currentSubject));
    }

    @And("the user logs out")
    public void logout() {
        mailPage.logout();
    }
}