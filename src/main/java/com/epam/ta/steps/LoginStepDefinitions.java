package com.epam.ta.steps;

import com.epam.ta.page.LoginPage;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class LoginStepDefinitions {

    private WebDriver driver;
    private LoginPage loginPage;

    public void LoginSteps() {
        this.driver = Hooks.driver;  // or inject via constructor
        this.loginPage = new LoginPage(driver);
    }

    @Given("the user is on the Yandex Mail login page")
    public void userIsOnLoginPage() {
        loginPage.navigateToMail();
    }

    @When("the user logs in with username {string} and password {string}")
    public void userLogsIn(String username, String password) {
        loginPage.login(username, password);
    }

    @Then("the user should be logged in successfully")
    public void userShouldBeLoggedIn() {
        Assert.assertTrue("User is not logged in", loginPage.isUserLoggedIn());
    }
}
