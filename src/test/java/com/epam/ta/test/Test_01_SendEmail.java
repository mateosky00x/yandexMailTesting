package com.epam.ta.test;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.model.User;
import com.epam.ta.page.LoginPage;
import com.epam.ta.page.MailPage;
import com.epam.ta.service.UserCreator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.*;

public class Test_01_SendEmail {

    private WebDriver driver;
    private LoginPage loginPage;
    private MailPage mailPage;


    @Before
    public void setUp() {
        driver = DriverSingleton.getDriver();
        loginPage = new LoginPage(driver);
        mailPage = new MailPage(driver);
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

    @After
    public void tearDown() {
        DriverSingleton.closeDriver();
    }
}
