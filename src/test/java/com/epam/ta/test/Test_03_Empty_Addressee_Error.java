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

public class Test_03_Empty_Addressee_Error {

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

    @After
    public void tearDown() {
        DriverSingleton.closeDriver();
    }
}
