import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class Test_03_Empty_Addressee_Error {
    private WebDriver driver;
    private YandexMailPage yandexMailPage;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        yandexMailPage = new YandexMailPage(driver);
    }

    @Test
    public void testSendWithoutAddresseeShowsError() {
        // Test data
        String subject = "Test Missing Addressee";
        String body = "This email has no recipient.";

        // Test credentials
        String testUser = "mateotester@yandex.com";
        String testPass = "TestingEPAM";

        // 1. Navigate and login
        yandexMailPage.navigateToMail();
        yandexMailPage.login(testUser, testPass);
        assertTrue("Login failed", yandexMailPage.isUserLoggedIn());

        // 2. Compose email without recipient
        yandexMailPage.composeEmailWithoutRecipient(subject, body);

        // 3. Try to send email
        yandexMailPage.sendEmail();

        // 4. Verify error pop-up appears
        assertTrue("Error pop-up not displayed", yandexMailPage.isErrorPopupDisplayed());

        // 5. Verify error message text (optional, adjust message accordingly)
        String expectedMessage = "Message not sent";
        assertTrue("Incorrect error message", yandexMailPage.getErrorPopupText().contains(expectedMessage));

        // 6. Close pop-up
        yandexMailPage.closeErrorPopup();

        // 7. Logout
        yandexMailPage.logout();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
