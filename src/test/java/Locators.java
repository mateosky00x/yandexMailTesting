public class Locators {
    // Login Page
    public static final String LOGIN_BUTTON = "a[href*='auth']";
    public static final String USERNAME_FIELD = "passp-field-login";
    public static final String PASSWORD_FIELD = "passp-field-passwd";
    public static final String SUBMIT_BUTTON = "button[type='submit']";
    public static final String NEXT_BUTTON = "button[data-t='button:action:passp:sign-in']";

    // Mail Interface
    public static final String COMPOSE_BUTTON = "a.Button2[href='#compose']";
    public static final String RECIPIENT_FIELD = "div.composeYabbles";
    public static final String SUBJECT_FIELD = "input[name='subject']";
    public static final String BODY_FIELD = "div.cke_wysiwyg_div";
    public static final String CLOSE_BUTTON = "(//button[@aria-label='Close'])[1]";
    public static final String SEND_BUTTON = "//button[.//span[text()='Send']]";

    // Folders
    public static final String DRAFTS_FOLDER = "a[href='#draft'][class*='Folder-m__link']";
    public static final String SENT_FOLDER = "//a[@href='#sent' and contains(@class, 'qa-LeftColumn-FolderLink')]";
    public static final String DRAFT_MESSAGE = "//span[@title='%s']";

    // Draft management

    public static final String MESSAGE_SENT = "//span[text()='Message sent']";
    public static final String SELECT_MESSAGE = "(//div[@data-testid='messages-list_message-item_checkbox-container'])[1]";
    public static final String DELETE_MESSAGE = "//button[.//span[text()='Delete']]";

    // User Account
    public static final String LOGOUT_BUTTON = "//span[text()='Sign out']";
    public static final String IFRAME_ACCOUNT_FRAME = "//iframe[@class='UserWidget-Iframe']";
    // Interface Marker
    public static final String INTERFACE_LOADED = "UserID-name";

    // Error Handling
    public static final String ERROR_POPUP = "ComposeConfirmPopup-Title";
    public static final String ERROR_POPUP_CLOSE_BUTTON = "(//button[@data-lego='react'])[1]";

}