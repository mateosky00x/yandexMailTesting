// ✅ Test_02_Delete_Draft.java
import org.junit.Test;
import static org.junit.Assert.*;

public class Test_02_Delete_Draft extends BaseTest {
    @Test
    public void testDeleteDraft() {
        String recipient = "mateo.castilloa@cun.edu.co";
        String subject = "Test Draft Deletion";
        String body = "This is a test draft email to be deleted.";
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

        mailPage.saveAsDraft();
        mailPage.deleteDraft();

        mailPage.openDraftsFolder();
        assertFalse("Draft still exists after deletion", mailPage.isEmailInDrafts(subject));

        mailPage.logout();
    }
}
