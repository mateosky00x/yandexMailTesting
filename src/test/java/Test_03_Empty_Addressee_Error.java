// ✅ Test_03_Empty_Addressee_Error.java
import org.junit.Test;
import static org.junit.Assert.*;

public class Test_03_Empty_Addressee_Error extends BaseTest {
    @Test
    public void testSendWithoutAddresseeShowsError() {
        String subject = "Test Missing Addressee";
        String body = "This email has no recipient.";
        String testUser = "mateotester@yandex.com";
        String testPass = "TestingEPAM";

        loginPage.navigateToMail();
        loginPage.login(testUser, testPass);
        assertTrue("Login failed", loginPage.isUserLoggedIn());

        mailPage.composeEmailWithoutRecipient(subject, body);
        mailPage.sendEmail();

        assertTrue("Error pop-up not displayed", mailPage.isErrorPopupDisplayed());
        String expectedMessage = "Message not sent";
        assertTrue("Incorrect error message", mailPage.getErrorPopupText().contains(expectedMessage));

        mailPage.closeErrorPopup();
        mailPage.logout();
    }
}
