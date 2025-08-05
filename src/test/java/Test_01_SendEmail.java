// ✅ Test_01_SendEmail.java
import org.junit.Test;
import static org.junit.Assert.*;

public class Test_01_SendEmail extends BaseTest {
    @Test
    public void testSendEmailThroughDrafts() {
        String recipient = "mateo.castilloa@cun.edu.co";
        String subject = "Test Email";
        String body = "This is a test email body.";
        String testUser = "mateotester@yandex.com";
        String testPass = "TestingEPAM";

        loginPage.navigateToMail();
        loginPage.login(testUser, testPass);
        assertTrue("Login failed", loginPage.isUserLoggedIn());

        mailPage.composeEmail(recipient, subject, body);
        mailPage.saveAsDraft();

        mailPage.openDraftsFolder();
        assertTrue("Draft not found", mailPage.isEmailInDrafts(subject));
        mailPage.openDraftMessage(subject);
        mailPage.openDraftEmail(subject);

        mailPage.sendEmail();
        mailPage.openDraftsFolder();
        assertFalse("Draft still exists", mailPage.isEmailInDrafts(subject));

        mailPage.openSentFolder();
        assertTrue("Email not in sent", mailPage.isEmailInSent(subject));

        mailPage.logout();
    }
}