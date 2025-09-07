package test;
import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.model.User;
import com.epam.ta.page.LoginPage;
import com.epam.ta.page.MailPage;
import com.epam.ta.service.UserCreator;
import com.epam.ta.util.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

@Listeners(TestListener.class)

public class Test_03_Empty_Addressee_Error {

    private WebDriver driver;
    private LoginPage loginPage;
    private MailPage mailPage;

    User testUser = UserCreator.withCredentialsFromProperty();

    @BeforeMethod
    public void setUp() {
        driver = DriverSingleton.getDriver();
        loginPage = new LoginPage(driver);
        mailPage = new MailPage(driver);
    }

    @Test
    public void testSendWithoutAddresseeShowsError() {
        String subject = "Test Missing Addressee";
        String body = "This email has no recipient.";

        loginPage.navigateToMail();
        loginPage.login(testUser.getUsername(), testUser.getPassword());
        assertTrue("Login failed", loginPage.isUserLoggedIn());

        mailPage.composeEmailWithoutRecipient(subject, body);
        mailPage.sendEmail();

        assertTrue("Error pop-up not displayed", mailPage.isErrorPopupDisplayed());

        String expectedMessage = "Message not sent";
        assertTrue("Incorrect error message", mailPage.getErrorPopupText().contains(expectedMessage));

        mailPage.closeErrorPopup();
        mailPage.logout();
    }

    @AfterMethod
    public void tearDown() {
        DriverSingleton.closeDriver();
    }
}
