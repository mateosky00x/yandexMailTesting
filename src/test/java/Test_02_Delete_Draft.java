import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class Test_02_Delete_Draft {
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
    public void testDeleteDraft() {
        // Test data
        String recipient = "mateo.castilloa@cun.edu.co";
        String subject = "Test Draft Deletion";
        String body = "This is a test draft email to be deleted.";

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

        // 4. Verify draft exists
        yandexMailPage.openDraftsFolder();
        yandexMailPage.openDraftMessage(subject);
        assertTrue("Draft not found", yandexMailPage.isEmailInDrafts(subject));
        yandexMailPage.openDraftEmail(subject);

        // 5. Delete the draft
        yandexMailPage.saveAsDraft();
        yandexMailPage.deleteDraft();

        // 6. Verify draft is deleted
        yandexMailPage.openDraftsFolder();
        assertFalse("Draft still exists after deletion", yandexMailPage.isEmailInDrafts(subject));

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
