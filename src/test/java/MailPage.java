// ✅ MailPage.java
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.util.List;

public class MailPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public MailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
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
        click(Locators.CLOSE_BUTTON);
    }

    public void openDraftsFolder() {
        click(Locators.DRAFTS_FOLDER);
    }

    public void openSentFolder() {
        click(Locators.SENT_FOLDER);
    }

    public boolean isEmailInDrafts(String subject) {
        try {
            return driver.findElement(Locators.draftMessage(subject)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isEmailInSent(String subject) {
        try {
            return driver.findElement(Locators.draftMessage(subject)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void openDraftMessage(String subject) {
        click(Locators.draftMessage(subject));
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