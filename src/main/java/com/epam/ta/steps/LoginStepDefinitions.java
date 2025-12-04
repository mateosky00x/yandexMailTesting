package com.epam.ta.steps;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.factory.PageFactory;
import com.epam.ta.model.User;
import com.epam.ta.page.LoginPage;
import com.epam.ta.page.MailPage;
import com.epam.ta.service.UserCreator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static junit.framework.TestCase.assertTrue;

public class LoginStepDefinitions {

    private WebDriver driver;
    private LoginPage loginPage;
    private MailPage mailPage;

    private final User user = UserCreator.withCredentialsFromProperty();

    @Given("the user opens the Yandex Mail login page")
    public void open_yandex_login_page() {
        driver = DriverSingleton.getDriver();
        loginPage = PageFactory.getPage(LoginPage.class, driver);
        mailPage = PageFactory.getPage(MailPage.class, driver);

        loginPage.navigateToMail();
    }

    @When("the user enters valid credentials")
    public void user_enters_valid_credentials() {
        loginPage.login(user.getUsername(), user.getPassword());
    }

    @Then("the user should be logged into the mailbox")
    public void user_should_be_logged_in() {
        assertTrue("Login failed", loginPage.isUserLoggedIn());
    }
}