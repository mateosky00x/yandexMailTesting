package com.epam.ta.page;

import com.epam.ta.model.AbstractPage;
import com.epam.ta.core.Button;
import com.epam.ta.core.TextBox;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static com.epam.ta.page.Locators.DRAFT_MESSAGE;

public class MailPage extends AbstractPage {

    // Elements from core
    private Button composeButton;
    private TextBox recipientField;
    private TextBox subjectField;
    private TextBox bodyField;
    private Button closeButton;
    private Button sendButton;
    private Button draftsFolder;
    private Button sentFolder;
    private Button selectMessage;
    private Button deleteMessage;
    private Button errorPopupCloseButton;

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
    }

    @Override
    protected AbstractPage openPage() {
        throw new UnsupportedOperationException("MailPage does not support direct openPage()");
    }

    public void composeEmail(String recipient, String subject, String body) {
        composeButton.click();
        recipientField.enterText(recipient);
        subjectField.enterText(subject);
        bodyField.enterText(body);
    }

    public void composeEmailWithoutRecipient(String subject, String body) {
        composeButton.click();
        subjectField.enterText(subject);
        bodyField.enterText(body);
    }

    public void saveAsDraft() {
        wait.until(ExpectedConditions.presenceOfElementLocated(Locators.CLOSE_BUTTON));
        closeButton.click();
    }

    public void openDraftsFolder() {
        draftsFolder.click();
    }

    public void openSentFolder() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(Locators.SENT_FOLDER);
        wait.until(ExpectedConditions.elementToBeClickable(Locators.SENT_FOLDER));
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    public boolean isEmailInDrafts(String subject) {
        By draftEmail = By.xpath(String.format(DRAFT_MESSAGE, subject));
        try {
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.DRAFT_CONTAINER));
            return driver.findElement(draftEmail).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmailInSent(String subject) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(DRAFT_MESSAGE, subject))));
            return driver.findElement(By.xpath(String.format(DRAFT_MESSAGE, subject))).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void moveToElement() {
        Actions actions = new Actions(driver);
        WebElement sentButton = driver.findElement(Locators.SENT_FOLDER);
        actions.moveToElement(sentButton).perform();
    }

    public void openDraftMessage(String subject) {
        new Button(driver, By.xpath(String.format(DRAFT_MESSAGE, subject))).click();
    }

    public void openDraftEmail(String subject) {
        wait.until(ExpectedConditions.presenceOfElementLocated(Locators.SUBJECT_FIELD));
        List<WebElement> elements = driver.findElements(Locators.SUBJECT_FIELD);
        elements.stream()
                .filter(e -> e.getText().contains(subject))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public void sendEmail() {
        sendButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.MESSAGE_SENT));
    }

    public void deleteDraft() {
        selectMessage.click();
        deleteMessage.click();
    }

    public boolean isErrorPopupDisplayed() {
        try {
            return driver.findElement(Locators.ERROR_POPUP).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getErrorPopupText() {
        return driver.findElement(Locators.ERROR_POPUP).getText();
    }

    public void closeErrorPopup() {
        errorPopupCloseButton.click();
    }

    public void logout() {
        driver.findElement(Locators.INTERFACE_LOADED).click();
        WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(Locators.IFRAME_ACCOUNT_FRAME));
        driver.switchTo().frame(iframe);
        driver.findElement(Locators.LOGOUT_BUTTON).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.LOGOUT_SCREEN));
    }
}
