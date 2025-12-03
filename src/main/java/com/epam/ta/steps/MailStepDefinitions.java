package com.epam.ta.steps;

import com.epam.ta.page.MailPage;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class MailStepDefinitions {

    private WebDriver driver;
    private MailPage mailPage;

    public void MailSteps() {
        this.driver = Hooks.driver;   // or inject via DI
        this.mailPage = new MailPage(driver);
    }

    // ------------------------
    // COMPOSE EMAIL
    // ------------------------

    @When("the user composes an email to {string} with subject {string} and body {string}")
    public void composeEmail(String recipient, String subject, String body) {
        mailPage.composeEmail(recipient, subject, body);
    }

    @When("the user composes an email without recipient with subject {string} and body {string}")
    public void composeEmailWithoutRecipient(String subject, String body) {
        mailPage.composeEmailWithoutRecipient(subject, body);
    }

    @When("the user saves the email as draft")
    public void saveAsDraft() {
        mailPage.saveAsDraft();
    }

    @And("the user opens the drafts folder")
    public void openDrafts() {
        mailPage.openDraftsFolder();
    }

    @Then("the email with subject {string} should appear in drafts")
    public void verifyDraft(String subject) {
        Assert.assertTrue("Draft not found", mailPage.isEmailInDrafts(subject));
    }

    // ------------------------
    // OPEN / EDIT DRAFT
    // ------------------------

    @When("the user opens the draft with subject {string}")
    public void openDraftWithSubject(String subject) {
        mailPage.openDraftMessage(subject);
    }

    // ------------------------
    // SEND EMAIL
    // ------------------------

    @When("the user sends the email")
    public void sendEmail() {
        mailPage.sendEmail();
    }

    @And("the user opens the sent folder")
    public void openSentFolder() {
        mailPage.moveToElement();
        mailPage.openSentFolder();
    }

    @Then("the email with subject {string} should appear in sent emails")
    public void verifySentEmail(String subject) {
        Assert.assertTrue("Email not found in sent folder", mailPage.isEmailInSent(subject));
    }

    // ------------------------
    // DELETE DRAFT
    // ------------------------

    @When("the user deletes the draft")
    public void deleteDraft() {
        mailPage.deleteDraft();
    }

    // ------------------------
    // ERROR POPUP
    // ------------------------

    @Then("an error popup should be displayed")
    public void verifyErrorPopup() {
        Assert.assertTrue("Error popup was expected but not displayed", mailPage.isErrorPopupDisplayed());
    }

    @And("the error popup message should be {string}")
    public void verifyErrorText(String expectedText) {
        Assert.assertEquals(expectedText, mailPage.getErrorPopupText());
    }

    @When("the user closes the error popup")
    public void closeErrorPopup() {
        mailPage.closeErrorPopup();
    }

    // ------------------------
    // LOGOUT
    // ------------------------

    @When("the user logs out")
    public void logout() {
        mailPage.logout();
    }

    @Then("the logout screen should be displayed")
    public void verifyLogoutScreen() {
        Assert.assertTrue("Logout screen not displayed",
                mailPage.logoutScreen.isDisplayed());
    }
}
