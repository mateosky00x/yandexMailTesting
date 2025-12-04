package com.epam.ta.steps;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.factory.PageFactory;
import com.epam.ta.model.User;
import com.epam.ta.page.LoginPage;
import com.epam.ta.page.MailPage;
import com.epam.ta.service.UserCreator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class MailStepDefinitions {

    private WebDriver driver;
    private LoginPage loginPage;
    private MailPage mailPage;

    private final User user = UserCreator.withCredentialsFromProperty();
    private String dynamicSubject = "";

    // ---------------------------
    // SETUP
    // ---------------------------

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        driver = DriverSingleton.getDriver();
        loginPage = PageFactory.getPage(LoginPage.class, driver);
        mailPage = PageFactory.getPage(MailPage.class, driver);
        loginPage.navigateToMail();
    }

    @When("the user logs in")
    public void the_user_logs_in() {
        loginPage.login(user.getUsername(), user.getPassword());
        assertTrue("Login failed", loginPage.isUserLoggedIn());
    }

    // ---------------------------
    // COMPOSE
    // ---------------------------

    @When("the user composes an email with recipient {string}, subject {string} and body {string}")
    public void compose_email(String recipient, String subject, String body) {
        dynamicSubject = subject + "-" + System.currentTimeMillis();
        mailPage.composeEmail(recipient, dynamicSubject, body);
    }

    @When("the user composes an email without a recipient, with subject {string} and body {string}")
    public void compose_without_recipient(String subject, String body) {
        dynamicSubject = subject + "-" + System.currentTimeMillis();
        mailPage.composeEmailWithoutRecipient(dynamicSubject, body);
    }

    // ---------------------------
    // DRAFTS
    // ---------------------------

    @When("the user saves the email as draft")
    public void save_as_draft() {
        mailPage.saveAsDraft();
    }

    @When("the user opens the drafts folder")
    public void open_drafts_folder() {
        mailPage.openDraftsFolder();
    }

    @Then("the email with subject {string} should appear in drafts")
    public void email_should_appear_exact(String subject) {
        assertTrue("Draft not found", mailPage.isEmailInDrafts(subject));
    }

    @When("the user opens the draft email with subject containing {string}")
    public void open_draft_email(String subjectPart) {
        mailPage.openDraftMessage(subjectPart);
        mailPage.openDraftEmail(subjectPart);
    }

    @When("the user deletes the draft")
    public void delete_draft() {
        mailPage.deleteDraft();
    }

    @Then("the email with subject {string} should not appear in drafts")
    public void email_should_not_appear(String subject) {
        assertFalse("Draft still exists", mailPage.isEmailInDrafts(subject));
    }

    // ---------------------------
    // SENDING
    // ---------------------------

    @When("the user sends the email")
    public void send_email() {
        mailPage.sendEmail();
    }

    @When("the user opens the sent folder")
    public void open_sent_folder() {
        mailPage.moveToElement();
        mailPage.openSentFolder();
    }

    @Then("the email with subject containing {string} should appear in sent")
    public void email_in_sent(String subject) {
        assertTrue("Email not in sent", mailPage.isEmailInSent(subject));
    }

    // ---------------------------
    // ERROR POPUP
    // ---------------------------

    @When("the user tries to send the email")
    public void user_tries_to_send() {
        mailPage.sendEmail();
    }

    @Then("an error popup should be displayed")
    public void error_popup_displayed() {
        assertTrue("Error popup not displayed", mailPage.isErrorPopupDisplayed());
    }

    @Then("the popup message should contain {string}")
    public void popup_message(String text) {
        assertTrue("Incorrect error message",
                mailPage.getErrorPopupText().contains(text));
    }

    @When("the user closes the popup")
    public void close_popup() {
        mailPage.closeErrorPopup();
    }

    // ---------------------------
    // LOGOUT
    // ---------------------------

    @Then("the user logs out")
    public void user_logs_out() {
        mailPage.logout();
        DriverSingleton.closeDriver();
    }
}