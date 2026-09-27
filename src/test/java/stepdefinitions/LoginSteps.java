package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;

public class LoginSteps {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    private LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(DriverFactory.getDriver(), ConfigReader.getExplicitWait());
        }
        return loginPage;
    }

    private DashboardPage dashboardPage() {
        if (dashboardPage == null) {
            dashboardPage = new DashboardPage(DriverFactory.getDriver(), ConfigReader.getExplicitWait());
        }
        return dashboardPage;
    }

    @Given("I am on the OrangeHRM login page")
    public void i_am_on_the_orange_hrm_login_page() {
        Assert.assertTrue(loginPage().isLoginPageDisplayed(), "Login page was not displayed.");
    }

    @Given("I log in with valid credentials")
    public void i_log_in_with_valid_credentials() {
        loginPage().login(ConfigReader.getUsername(), ConfigReader.getPassword());
    }

    @When("I enter a valid username")
    public void i_enter_a_valid_username() {
        loginPage().enterUsername(ConfigReader.getUsername());
    }

    @When("I enter a valid password")
    public void i_enter_a_valid_password() {
        loginPage().enterPassword(ConfigReader.getPassword());
    }

    @When("I enter an invalid username {string}")
    public void i_enter_an_invalid_username(String username) {
        loginPage().enterUsername(username);
    }

    @When("I enter an invalid password {string}")
    public void i_enter_an_invalid_password(String password) {
        loginPage().enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        loginPage().clickLogin();
    }

    @Then("I should be redirected to the Dashboard")
    public void i_should_be_redirected_to_the_dashboard() {
        Assert.assertTrue(dashboardPage().isDashboardDisplayed(), "Dashboard was not displayed after login.");
    }

    @Then("I should see an {string} error message")
    public void i_should_see_an_error_message(String expectedMessage) {
        Assert.assertTrue(loginPage().isErrorMessageDisplayed(), "Expected error message was not displayed.");
        Assert.assertTrue(loginPage().getErrorMessage().toLowerCase().contains(expectedMessage.toLowerCase()),
                "Error message did not contain expected text: " + expectedMessage);
    }
}
