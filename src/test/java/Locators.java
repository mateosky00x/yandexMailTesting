// ✅ Locators.java
import org.openqa.selenium.By;

public class Locators {
    // Login Page
    public static final By LOGIN_BUTTON = By.cssSelector("a[href*='auth']");
    public static final By USERNAME_FIELD = By.id("passp-field-login");
    public static final By PASSWORD_FIELD = By.id("passp-field-passwd");
    public static final By SUBMIT_BUTTON = By.cssSelector("button[type='submit']");
    public static final By NEXT_BUTTON = By.cssSelector("button[data-t='button:action:passp:sign-in']");

    // Mail Interface
    public static final By COMPOSE_BUTTON = By.cssSelector("a.Button2[href='#compose']");
    public static final By RECIPIENT_FIELD = By.cssSelector("div.composeYabbles");
    public static final By SUBJECT_FIELD = By.cssSelector("input[name='subject']");
    public static final By BODY_FIELD = By.cssSelector("div.cke_wysiwyg_div");
    public static final By CLOSE_BUTTON = By.xpath("(//button[@aria-label='Close'])[1]");
    public static final By SEND_BUTTON = By.xpath("//button[.//span[text()='Send']]");

    // Folders
    public static final By DRAFTS_FOLDER = By.cssSelector("a[href='#draft'][class*='Folder-m__link']");
    public static final By SENT_FOLDER = By.xpath("//a[@href='#sent' and contains(@class, 'qa-LeftColumn-FolderLink')]");
    public static By draftMessage(String subject) {
        return By.xpath(String.format("//span[@title='%s']", subject));
    }

    // Draft management
    public static final By MESSAGE_SENT = By.xpath("//span[text()='Message sent']");
    public static final By SELECT_MESSAGE = By.xpath("(//div[@data-testid='messages-list_message-item_checkbox-container'])[1]");
    public static final By DELETE_MESSAGE = By.xpath("//button[.//span[text()='Delete']]");

    // User Account
    public static final By LOGOUT_BUTTON = By.xpath("//span[text()='Sign out']");
    public static final By IFRAME_ACCOUNT_FRAME = By.xpath("//iframe[@class='UserWidget-Iframe']");
    public static final By INTERFACE_LOADED = By.className("UserID-name");

    // Error Handling
    public static final By ERROR_POPUP = By.className("ComposeConfirmPopup-Title");
    public static final By ERROR_POPUP_CLOSE_BUTTON = By.xpath("(//button[@data-lego='react'])[1]");
    public static final By LOGOUT_SCREEN = By.xpath("//span[normalize-space(text())='Log in to another account?'][@data-variant='heading-l']");
}