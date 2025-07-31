import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
public class YandexMailPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public YandexMailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
    }

    public void navigateToMail() {
        driver.get("https://360.yandex.com/mail/");
    }

    public void login(String username, String password) {
        click(By.cssSelector(Locators.LOGIN_BUTTON));
        sendKeys(By.id(Locators.USERNAME_FIELD), username);
        clickFirst(By.cssSelector(Locators.SUBMIT_BUTTON));
        sendKeys(By.id(Locators.PASSWORD_FIELD), password);
        click(By.cssSelector(Locators.NEXT_BUTTON));
        waitForInterface();
    }

    public boolean isUserLoggedIn() {
        return isDisplayed(By.className(Locators.INTERFACE_LOADED));
    }

    public void composeEmail(String recipient, String subject, String body) {
        click(By.cssSelector(Locators.COMPOSE_BUTTON));
        sendKeys(By.cssSelector(Locators.RECIPIENT_FIELD), recipient);
        sendKeys(By.cssSelector(Locators.SUBJECT_FIELD), subject);
        sendKeys(By.cssSelector(Locators.BODY_FIELD), body);
    }

    public void saveAsDraft() {
        click(By.xpath(Locators.CLOSE_BUTTON));
    }

    public void openDraftsFolder() {

        click(By.cssSelector(Locators.DRAFTS_FOLDER));

    }

    public boolean isEmailInDrafts(String subject) {
        String locator = String.format(Locators.DRAFT_MESSAGE, subject);
        try {
            return driver.findElement(By.xpath(locator)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void openDraftMessage(String draftTitle) {
        String locator = String.format(Locators.DRAFT_MESSAGE, draftTitle);
        click(By.xpath(locator));
    }

    public void deleteDraft () {
        click(By.xpath(Locators.SELECT_MESSAGE));
        click(By.xpath(Locators.DELETE_MESSAGE));
    }

    public void openDraftEmail(String subject) {
        openFromList(Locators.SUBJECT_FIELD, subject);
    }

    public void sendEmail() {
        click(By.xpath(Locators.SEND_BUTTON));
    }

    public void seeNotificationEmailSent() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.MESSAGE_SENT)));

    }

    public void openSentFolder() {
        click(By.xpath(Locators.SENT_FOLDER));
    }

    public boolean isEmailInSent(String subject) {

        String locator = String.format(Locators.DRAFT_MESSAGE, subject);
        try {
            return driver.findElement(By.xpath(locator)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void logout() {
        click(By.className(Locators.INTERFACE_LOADED));
        WebElement iframeElement = driver.findElement(By.xpath(Locators.IFRAME_ACCOUNT_FRAME));
        driver.switchTo().frame(iframeElement);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.LOGOUT_BUTTON)));
        click(By.xpath(Locators.LOGOUT_BUTTON));

    }


    // Helper methods
    private void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void clickFirst(By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator)).get(0).click();
    }

    private void sendKeys(By locator, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
    }

    private boolean isDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private boolean isInList(String listLocator, String text) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(listLocator)));
        return driver.findElements(By.cssSelector(listLocator)).stream()
                .anyMatch(element -> element.getText().contains(text));
    }

    private void openFromList(String listLocator, String text) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(listLocator)));
        driver.findElements(By.cssSelector(listLocator)).stream()
                .filter(element -> element.getText().contains(text))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    private void waitForInterface() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.className(Locators.INTERFACE_LOADED)));
    }

    public void composeEmailWithoutRecipient(String subject, String body) {
        click(By.cssSelector(Locators.COMPOSE_BUTTON));
        sendKeys(By.cssSelector(Locators.SUBJECT_FIELD), subject);
        sendKeys(By.cssSelector(Locators.BODY_FIELD), body);
    }

    public boolean isErrorPopupDisplayed() {
        try {
            return driver.findElement(By.className(Locators.ERROR_POPUP)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getErrorPopupText() {
        return driver.findElement(By.className(Locators.ERROR_POPUP)).getText();
    }

    public void closeErrorPopup() {
        click(By.xpath(Locators.ERROR_POPUP_CLOSE_BUTTON));
    }
}
