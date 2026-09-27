package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.AdminPage;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.TestData;

public class AdminSteps {

    private AdminPage adminPage;
    private String lastCreatedUsername;

    private AdminPage adminPage() {
        if (adminPage == null) {
            adminPage = new AdminPage(DriverFactory.getDriver(), ConfigReader.getExplicitWait());
        }
        return adminPage;
    }

    @When("I search for the system user {string}")
    public void i_search_for_the_system_user(String username) {
        adminPage().searchUserByUsername(username);
    }

    @Then("the user {string} should be displayed in the user list")
    public void the_user_should_be_displayed_in_the_user_list(String username) {
        Assert.assertTrue(adminPage().isUserInResults(username),
                "System user '" + username + "' was not found in the user list.");
    }

    @When("I add a new system user with a randomly generated username")
    public void i_add_a_new_system_user_with_a_randomly_generated_username() {
        lastCreatedUsername = TestData.randomUsername();
        String password = TestData.randomPassword();

        adminPage().clickAddUser();
        adminPage().addNewSystemUser("ESS", ConfigReader.getUsername(), "Enabled", lastCreatedUsername, password);
    }

    @Then("the newly created user should be displayed in the user list")
    public void the_newly_created_user_should_be_displayed_in_the_user_list() {
        adminPage().searchUserByUsername(lastCreatedUsername);
        Assert.assertTrue(adminPage().isUserInResults(lastCreatedUsername),
                "Newly created user '" + lastCreatedUsername + "' was not found in the user list.");
    }
}
