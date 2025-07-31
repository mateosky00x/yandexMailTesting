import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class Test_01_SendEmail {
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
    public void testSendEmailThroughDrafts() {
        // Test data
        String recipient = "mateo.castilloa@cun.edu.co";
        String subject = "Test Email" ;
        String body = "This is a test email body.";

        // Test credentials
        String testUser = "mateotester@yandex.com";
        String testPass = "TestingEPAM";

        // 1. Navigate to Yandex Mail
        yandexMailPage.navigateToMail();

        // 2. Login
        yandexMailPage.login(testUser, testPass);
        assertTrue("Login failed", yandexMailPage.isUserLoggedIn());

        // 3. Create and save draft
        yandexMailPage.composeEmail(recipient, subject, body);
        yandexMailPage.saveAsDraft();

        // 4. Verify draft
        yandexMailPage.openDraftsFolder();
        yandexMailPage.openDraftMessage(subject);
        assertTrue("Draft not found", yandexMailPage.isEmailInDrafts(subject));
        yandexMailPage.openDraftEmail(subject);

        // 5. Send email
        yandexMailPage.sendEmail();

        // 6. Confirm email sent

        yandexMailPage.seeNotificationEmailSent();
        yandexMailPage.openDraftsFolder();
        assertFalse("Draft still exists", yandexMailPage.isEmailInDrafts(subject));
        yandexMailPage.openSentFolder();
        assertTrue("Email not in sent", yandexMailPage.isEmailInSent(subject));

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