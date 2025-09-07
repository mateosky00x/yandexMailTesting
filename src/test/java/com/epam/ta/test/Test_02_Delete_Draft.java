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
public class Test_02_Delete_Draft {

    private WebDriver driver;
    private LoginPage loginPage;
    private MailPage mailPage;

    private User testUser = UserCreator.withCredentialsFromProperty();

    @BeforeMethod
    public void setUp() {
        driver = DriverSingleton.getDriver();
        loginPage = PageFactory.getPage(LoginPage.class, driver);
        mailPage = PageFactory.getPage(MailPage.class, driver);
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

    @AfterMethod
    public void tearDown() {
        DriverSingleton.closeDriver();
    }
}

