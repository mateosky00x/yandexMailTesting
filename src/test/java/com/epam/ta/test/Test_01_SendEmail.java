package com.epam.ta.test;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.factory.PageFactory;
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

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

@Listeners(TestListener.class)
public class Test_01_SendEmail {

    private WebDriver driver;
    private LoginPage loginPage;
    private MailPage mailPage;

    @BeforeMethod
    public void setUp() {
        driver = DriverSingleton.getDriver();
        //Getting pages from factory
        loginPage = PageFactory.getPage(LoginPage.class, driver);
        mailPage = PageFactory.getPage(MailPage.class, driver);
    }

    @Test
    public void testSendEmailThroughDrafts() {
        User testUser = UserCreator.withCredentialsFromProperty();
        String recipient = "mateo.castilloa@cun.edu.co";
        String subject = "Test Email-" + System.currentTimeMillis();
        String body = "This is a test email body.";


        loginPage.navigateToMail();
        loginPage.login(testUser.getUsername(), testUser.getPassword());
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

        mailPage.moveToElement();
        mailPage.openSentFolder();
        assertTrue("Email not in sent", mailPage.isEmailInSent(subject));

        mailPage.logout();
    }

    @AfterMethod
    public void tearDown() {
        DriverSingleton.closeDriver();
    }
}
