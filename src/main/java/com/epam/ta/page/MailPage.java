package com.epam.ta.page;

import com.epam.ta.model.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static com.epam.ta.page.Locators.DRAFT_MESSAGE;

public class MailPage extends AbstractPage {

    public MailPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected AbstractPage openPage() {
        throw new UnsupportedOperationException("MailPage does not support direct openPage()");
    }

    public void composeEmail(String recipient, String subject, String body) {
        click(Locators.COMPOSE_BUTTON);
        sendKeys(Locators.RECIPIENT_FIELD, recipient);
        sendKeys(Locators.SUBJECT_FIELD, subject);
        sendKeys(Locators.BODY_FIELD, body);
    }

    public void composeEmailWithoutRecipient(String subject, String body) {
        click(Locators.COMPOSE_BUTTON);
        sendKeys(Locators.SUBJECT_FIELD, subject);
        sendKeys(Locators.BODY_FIELD, body);
    }

    public void saveAsDraft() {
        wait.until(ExpectedConditions.presenceOfElementLocated(Locators.CLOSE_BUTTON));
        click(Locators.CLOSE_BUTTON);
    }

    public void openDraftsFolder() {
        click(Locators.DRAFTS_FOLDER);
    }

    public void openSentFolder() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(Locators.SENT_FOLDER);
        wait.until(ExpectedConditions.elementToBeClickable(Locators.SENT_FOLDER));
        // Locate the element using standard WebDriver methods
        jsExecutor.executeScript("arguments[0].click();", element);
        //click(Locators.SENT_FOLDER);
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


    public void moveToElement(){
        Actions actions = new Actions(driver);
        WebElement sentButton = driver.findElement(Locators.SENT_FOLDER);
        actions.moveToElement(sentButton);
    }
    public void openDraftMessage(String subject) {

        click(By.xpath(String.format(DRAFT_MESSAGE, subject)));
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
        click(Locators.SEND_BUTTON);
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.MESSAGE_SENT));
    }

    public void deleteDraft() {
        click(Locators.SELECT_MESSAGE);
        click(Locators.DELETE_MESSAGE);
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
        click(Locators.ERROR_POPUP_CLOSE_BUTTON);
    }

    public void logout() {
        click(Locators.INTERFACE_LOADED);
        WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(Locators.IFRAME_ACCOUNT_FRAME));
        driver.switchTo().frame(iframe);
        click(Locators.LOGOUT_BUTTON);
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.LOGOUT_SCREEN));
    }

    private void click(By locator) {

        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void sendKeys(By locator, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
    }
}
