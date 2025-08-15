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

public class Test_02_Delete_Draft {

    private WebDriver driver;
    private LoginPage loginPage;
    private MailPage mailPage;

    User testUser = UserCreator.withCredentialsFromProperty();

    @Before
    public void setUp() {
        driver = DriverSingleton.getDriver();
        loginPage = new LoginPage(driver);
        mailPage = new MailPage(driver);
    }

    @Test
    public void testDeleteDraft() {
        String recipient = "mateo.castilloa@cun.edu.co";
        String subject = "Test Draft Deletion";
        String body = "This is a test draft email to be deleted.";

        loginPage.navigateToMail();
        loginPage.login(testUser.getUsername(), testUser.getPassword());
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

    @After
    public void tearDown() {
        DriverSingleton.closeDriver();
    }
}

