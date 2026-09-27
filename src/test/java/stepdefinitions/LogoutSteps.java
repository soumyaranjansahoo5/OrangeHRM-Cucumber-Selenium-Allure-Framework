package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.HeaderPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;

public class LogoutSteps {

    private HeaderPage headerPage;
    private LoginPage loginPage;

    private HeaderPage headerPage() {
        if (headerPage == null) {
            headerPage = new HeaderPage(DriverFactory.getDriver(), ConfigReader.getExplicitWait());
        }
        return headerPage;
    }

    private LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(DriverFactory.getDriver(), ConfigReader.getExplicitWait());
        }
        return loginPage;
    }

    @When("I click the user dropdown and select logout")
    public void i_click_the_user_dropdown_and_select_logout() {
        headerPage().logout();
    }

    @Then("I should be redirected to the Login page")
    public void i_should_be_redirected_to_the_login_page() {
        Assert.assertTrue(loginPage().isLoginPageDisplayed(), "User was not redirected to the Login page after logout.");
    }
}
