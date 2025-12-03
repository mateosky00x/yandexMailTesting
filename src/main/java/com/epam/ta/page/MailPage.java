package com.epam.ta.page;

import com.epam.ta.core.Button;
import com.epam.ta.core.TextBox;
import com.epam.ta.core.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class MailPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(MailPage.class);

    // MailPage specific locators
    private static final By COMPOSE_BUTTON = By.cssSelector("a.Button2[href='#compose']");
    private static final By RECIPIENT_FIELD = By.cssSelector("div.composeYabbles");
    private static final By SUBJECT_FIELD = By.cssSelector("input[name='subject']");
    private static final By BODY_FIELD = By.cssSelector("div.cke_wysiwyg_div");
    private static final By CLOSE_BUTTON = By.xpath("(//button[@aria-label='Close'])[1]");
    private static final By SEND_BUTTON = By.xpath("//span[text()='Send']//ancestor::button");
    private static final By DRAFTS_FOLDER = By.cssSelector("a[href='#draft'][class*='Folder-m__link']");
    private static final By SENT_FOLDER = By.xpath("//span[text()='Sent']");
    private static final String DRAFT_MESSAGE = "//span[@title='%s']";
    private static final By DRAFT_CONTAINER = By.xpath("//div[@data-testid='messages-list_scroller_container']");
    private static final By MESSAGE_SENT = By.xpath("//span[text()='Message sent']");
    private static final By SELECT_MESSAGE = By.xpath("(//div[@data-testid='messages-list_message-item_checkbox-container'])[1]");
    private static final By DELETE_MESSAGE = By.xpath("//button[.//span[text()='Delete']]");
    private static final By LOGOUT_BUTTON = By.xpath("//span[text()='Sign out']");
    private static final By IFRAME_ACCOUNT_FRAME = By.xpath("//iframe[@class='UserWidget-Iframe']");
    private static final By INTERFACE_LOADED = By.className("UserID-name");
    private static final By ERROR_POPUP = By.className("ComposeConfirmPopup-Title");
    private static final By ERROR_POPUP_CLOSE_BUTTON = By.xpath("(//button[@data-lego='react'])[1]");
    private static final By LOGOUT_SCREEN = By.xpath("//span[normalize-space(text())='Log in to another account?'][@data-variant='heading-l']");

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
    public Label logoutScreen;

    public MailPage(WebDriver driver) {
        super(driver);
        logger.info("Initializing MailPage");

        this.composeButton = new Button(driver, COMPOSE_BUTTON);
        this.recipientField = new TextBox(driver, RECIPIENT_FIELD);
        this.subjectField = new TextBox(driver, SUBJECT_FIELD);
        this.bodyField = new TextBox(driver, BODY_FIELD);
        this.closeButton = new Button(driver, CLOSE_BUTTON);
        this.sendButton = new Button(driver, SEND_BUTTON);
        this.draftsFolder = new Button(driver, DRAFTS_FOLDER);
        this.sentFolder = new Button(driver, SENT_FOLDER);
        this.selectMessage = new Button(driver, SELECT_MESSAGE);
        this.deleteMessage = new Button(driver, DELETE_MESSAGE);
        this.errorPopupCloseButton = new Button(driver, ERROR_POPUP_CLOSE_BUTTON);

        this.draftContainer = new Label(driver, DRAFT_CONTAINER);
        this.messageSent = new Label(driver, MESSAGE_SENT);
        this.errorPopup = new Label(driver, ERROR_POPUP);
        this.logoutButton = new Label(driver, LOGOUT_BUTTON);
        this.interfaceLoaded = new Label(driver, INTERFACE_LOADED);
        this.logoutScreen = new Label(driver, LOGOUT_SCREEN);

        logger.debug("MailPage elements initialized successfully");
    }

    @Override
    protected BasePage openPage() {
        throw new UnsupportedOperationException("MailPage does not support direct openPage()");
    }

    public void composeEmail(String recipient, String subject, String body) {
        logger.info("Composing email to: {} with subject: {}", recipient, subject);
        try {
            composeButton.click();
            logger.debug("Compose button clicked");

            recipientField.typeText(recipient);
            logger.debug("Recipient entered: {}", recipient);

            subjectField.typeText(subject);
            logger.debug("Subject entered: {}", subject);

            bodyField.typeText(body);
            logger.debug("Email body entered");

            logger.info("Email composition completed successfully");
        } catch (Exception e) {
            logger.error("Failed to compose email", e);
            throw e;
        }
    }

    public void composeEmailWithoutRecipient(String subject, String body) {
        logger.info("Composing email without recipient - subject: {}", subject);
        try {
            composeButton.click();
            logger.debug("Compose button clicked");

            subjectField.typeText(subject);
            logger.debug("Subject entered: {}", subject);

            bodyField.typeText(body);
            logger.debug("Email body entered");

            logger.info("Email composition (without recipient) completed");
        } catch (Exception e) {
            logger.error("Failed to compose email without recipient", e);
            throw e;
        }
    }

    public void saveAsDraft() {
        logger.info("Saving email as draft");
        try {
            closeButton.click();
            logger.info("Email saved as draft successfully");
        } catch (Exception e) {
            logger.error("Failed to save email as draft", e);
            throw e;
        }
    }

    public void openDraftsFolder() {
        logger.info("Opening drafts folder");
        try {
            draftsFolder.click();
            logger.info("Drafts folder opened successfully");
        } catch (Exception e) {
            logger.error("Failed to open drafts folder", e);
            throw e;
        }
    }

    public void openSentFolder() {
        logger.info("Opening sent folder");
        try {
            // clicking with JS
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            WebElement element = driver.findElement(SENT_FOLDER);
            jsExecutor.executeScript("arguments[0].click();", element);
            logger.info("Sent folder opened successfully");
        } catch (Exception e) {
            logger.error("Failed to open sent folder", e);
            throw e;
        }
    }

    public boolean isEmailInDrafts(String subject) {
        logger.debug("Checking if email '{}' is in drafts", subject);
        By draftEmail = By.xpath(String.format(DRAFT_MESSAGE, subject));
        try {
            draftContainer.isDisplayed();
            boolean isPresent = driver.findElement(draftEmail).isDisplayed();
            logger.debug("Email '{}' in drafts: {}", subject, isPresent);
            return isPresent;
        } catch (Exception e) {
            logger.debug("Email '{}' not found in drafts", subject);
            return false;
        }
    }

    public boolean isEmailInSent(String subject) {
        logger.debug("Checking if email '{}' is in sent folder", subject);
        try {
            By sentEmail = By.xpath(String.format(DRAFT_MESSAGE, subject));
            boolean isPresent = driver.findElement(sentEmail).isDisplayed();
            logger.debug("Email '{}' in sent: {}", subject, isPresent);
            return isPresent;
        } catch (NoSuchElementException e) {
            logger.debug("Email '{}' not found in sent folder", subject);
            return false;
        }
    }

    public void moveToElement() {
        logger.debug("Moving to sent folder element");
        try {
            Actions actions = new Actions(driver);
            WebElement sentButtonElement = driver.findElement(SENT_FOLDER);
            actions.moveToElement(sentButtonElement).perform();
            logger.debug("Successfully moved to sent folder element");
        } catch (Exception e) {
            logger.error("Failed to move to sent folder element", e);
            throw e;
        }
    }

    public void openDraftMessage(String subject) {
        logger.info("Opening draft message: {}", subject);
        try {
            new Button(driver, By.xpath(String.format(DRAFT_MESSAGE, subject))).click();
            logger.info("Draft message '{}' opened successfully", subject);
        } catch (Exception e) {
            logger.error("Failed to open draft message: {}", subject, e);
            throw e;
        }
    }

    public void openDraftEmail(String subject) {
        logger.info("Opening draft email: {}", subject);
        try {
            List<WebElement> elements = driver.findElements(SUBJECT_FIELD);
            elements.stream()
                    .filter(e -> e.getText().contains(subject))
                    .findFirst()
                    .ifPresent(WebElement::click);
            logger.info("Draft email '{}' opened successfully", subject);
        } catch (Exception e) {
            logger.error("Failed to open draft email: {}", subject, e);
            throw e;
        }
    }

    public void sendEmail() {
        logger.info("Sending email");
        try {
            sendButton.click();
            messageSent.isDisplayed();
            logger.info("Email sent successfully");
        } catch (Exception e) {
            logger.error("Failed to send email", e);
            throw e;
        }
    }

    public void deleteDraft() {
        logger.info("Deleting draft");
        try {
            selectMessage.click();
            logger.debug("Message selected");
            deleteMessage.click();
            logger.info("Draft deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete draft", e);
            throw e;
        }
    }

    public boolean isErrorPopupDisplayed() {
        try {
            boolean isDisplayed = errorPopup.isDisplayed();
            logger.debug("Error popup displayed: {}", isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.debug("Error popup not displayed");
            return false;
        }
    }

    public String getErrorPopupText() {
        try {
            String errorText = errorPopup.getText();
            logger.debug("Error popup text: {}", errorText);
            return errorText;
        } catch (Exception e) {
            logger.error("Failed to get error popup text", e);
            throw e;
        }
    }

    public void closeErrorPopup() {
        logger.info("Closing error popup");
        try {
            errorPopupCloseButton.click();
            logger.info("Error popup closed successfully");
        } catch (Exception e) {
            logger.error("Failed to close error popup", e);
            throw e;
        }
    }

    public void logout() {
        logger.info("Starting logout process");
        try {
            interfaceLoaded.click();
            logger.debug("Interface button clicked");

            WebElement iframe = driver.findElement(IFRAME_ACCOUNT_FRAME);
            driver.switchTo().frame(iframe);
            logger.debug("Switched to account iframe");

            logoutButton.click();
            logger.debug("Logout button clicked");

            logoutScreen.isDisplayed();
            logger.info("Logout completed successfully");
        } catch (Exception e) {
            logger.error("Logout failed", e);
            throw e;
        }
    }
}