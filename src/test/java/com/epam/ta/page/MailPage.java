package com.epam.ta.page;

import com.epam.ta.core.Button;
import com.epam.ta.core.TextBox;
import com.epam.ta.core.Label;
import com.epam.ta.model.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static com.epam.ta.page.Locators.DRAFT_MESSAGE;

public class MailPage extends AbstractPage {

    private Button composeButton;
    private TextBox recipientField;
    private TextBox subjectField;
    private TextBox bodyField;
    private Button closeButton;
    private Button sendButton;
    private Button draftsFolder;
    final Button sentFolder;
    private Button selectMessage;
    private Button deleteMessage;
    private Button errorPopupCloseButton;

    private Label draftContainer;
    private Label messageSent;
    private Label errorPopup;
    private Label logoutButton;
    private Label interfaceLoaded;
    private Label logoutScreen;

    public MailPage(WebDriver driver) {
        super(driver);

        this.composeButton = new Button(driver, Locators.COMPOSE_BUTTON);
        this.recipientField = new TextBox(driver, Locators.RECIPIENT_FIELD);
        this.subjectField = new TextBox(driver, Locators.SUBJECT_FIELD);
        this.bodyField = new TextBox(driver, Locators.BODY_FIELD);
        this.closeButton = new Button(driver, Locators.CLOSE_BUTTON);
        this.sendButton = new Button(driver, Locators.SEND_BUTTON);
        this.draftsFolder = new Button(driver, Locators.DRAFTS_FOLDER);
        this.sentFolder = new Button(driver, Locators.SENT_FOLDER);
        this.selectMessage = new Button(driver, Locators.SELECT_MESSAGE);
        this.deleteMessage = new Button(driver, Locators.DELETE_MESSAGE);
        this.errorPopupCloseButton = new Button(driver, Locators.ERROR_POPUP_CLOSE_BUTTON);

        this.draftContainer = new Label(driver, Locators.DRAFT_CONTAINER);
        this.messageSent = new Label(driver, Locators.MESSAGE_SENT);
        this.errorPopup = new Label(driver, Locators.ERROR_POPUP);
        this.logoutButton = new Label(driver, Locators.LOGOUT_BUTTON);
        this.interfaceLoaded = new Label(driver, Locators.INTERFACE_LOADED);
        this.logoutScreen = new Label(driver, Locators.LOGOUT_SCREEN);
    }

    @Override
    protected AbstractPage openPage() {
        throw new UnsupportedOperationException("MailPage does not support direct openPage()");
    }

    public void composeEmail(String recipient, String subject, String body) {
        composeButton.click();
        recipientField.typeText(recipient);
        subjectField.typeText(subject);
        bodyField.typeText(body);
    }

    public void composeEmailWithoutRecipient(String subject, String body) {
        composeButton.click();
        subjectField.typeText(subject);
        bodyField.typeText(body);
    }

    public void saveAsDraft() {
        closeButton.click();
    }

    public void openDraftsFolder() {
        draftsFolder.click();
    }

    public void openSentFolder() {
        // clicking with JS
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(Locators.SENT_FOLDER);
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    public boolean isEmailInDrafts(String subject) {
        By draftEmail = By.xpath(String.format(DRAFT_MESSAGE, subject));
        try {
            draftContainer.isDisplayed();
            return driver.findElement(draftEmail).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmailInSent(String subject) {
        try {
            By sentEmail = By.xpath(String.format(DRAFT_MESSAGE, subject));
            return driver.findElement(sentEmail).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void moveToElement() {
        Actions actions = new Actions(driver);
        WebElement sentButtonElement = driver.findElement(Locators.SENT_FOLDER);
        actions.moveToElement(sentButtonElement).perform();
    }

    public void openDraftMessage(String subject) {
        new Button(driver, By.xpath(String.format(DRAFT_MESSAGE, subject))).click();
    }

    public void openDraftEmail(String subject) {
        List<WebElement> elements = driver.findElements(Locators.SUBJECT_FIELD);
        elements.stream()
                .filter(e -> e.getText().contains(subject))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public void sendEmail() {
        sendButton.click();
        messageSent.isDisplayed();
    }

    public void deleteDraft() {
        selectMessage.click();
        deleteMessage.click();
    }

    public boolean isErrorPopupDisplayed() {
        return errorPopup.isDisplayed();
    }

    public String getErrorPopupText() {
        return errorPopup.getText();
    }

    public void closeErrorPopup() {
        errorPopupCloseButton.click();
    }

    public void logout() {
        interfaceLoaded.click();
        WebElement iframe = driver.findElement(Locators.IFRAME_ACCOUNT_FRAME);
        driver.switchTo().frame(iframe);
        logoutButton.click();
        logoutScreen.isDisplayed();
    }
}

