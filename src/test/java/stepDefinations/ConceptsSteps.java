package stepDefinations;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObjects.LoginPageObject;
import pages.DashboardPage;
import sharedData.LoginCredentials;
import sharedData.TestContext;
import utils.ConfigReader;
import utils.DriverFactory;

import java.util.List;
import java.util.Map;

/**
 * Step definitions for src/test/resources/features/concepts_demo.feature.
 *
 * Covers: Background, Scenario Outline, a raw DataTable, and a custom
 * @DataTableType transformer. The Background step also (indirectly) proves
 * hook ordering is correct for @hooksDemo scenarios, since it can only run
 * once hooks.HooksOrderDemo's three @Before hooks have already fired.
 */
public class ConceptsSteps {

    private final TestContext context;
    private LoginPageObject loginPageObject;
    private DashboardPage dashboardPage;

    public ConceptsSteps(TestContext context) {
        this.context = context;
    }

    private LoginPageObject loginPageObject() {
        if (loginPageObject == null) {
            loginPageObject = new LoginPageObject(DriverFactory.getDriver(), ConfigReader.getExplicitWait());
        }
        return loginPageObject;
    }

    private DashboardPage dashboardPage() {
        if (dashboardPage == null) {
            dashboardPage = new DashboardPage(DriverFactory.getDriver(), ConfigReader.getExplicitWait());
        }
        return dashboardPage;
    }

    // ---------------------------------------------------------------
    // Background
    // ---------------------------------------------------------------
    @Given("I open the OrangeHRM login page")
    public void i_open_the_orange_hrm_login_page() {
        loginPageObject().open(ConfigReader.getUrl());
        Assert.assertTrue(loginPageObject().isLoginPageDisplayed(), "Login page did not load.");
    }

    // ---------------------------------------------------------------
    // Hooks demo - only the @Before side is verifiable from inside a step;
    // the full Before+After sequence is verified in HooksOrderDemo's final
    // @After hook, since the Afters haven't run yet at this point.
    // ---------------------------------------------------------------
    @Then("the hooks should have executed in the correct before/after order")
    public void the_hooks_should_have_executed_in_the_correct_before_after_order() {
        List<String> beforesSoFar = context.getHookExecutionLog();
        Assert.assertEquals(beforesSoFar,
                List.of("BEFORE-order-1", "BEFORE-order-2", "BEFORE-order-3"),
                "Before-hook ordering was incorrect at the point the scenario steps ran.");
    }

    // ---------------------------------------------------------------
    // Scenario Outline
    // ---------------------------------------------------------------
    @When("I attempt to log in with username {string} and password {string}")
    public void i_attempt_to_log_in_with_username_and_password(String username, String password) {
        loginPageObject().login(username, password);
    }

    @Then("I should see the {string} outcome")
    public void i_should_see_the_outcome(String expectedResult) {
        if ("success".equalsIgnoreCase(expectedResult)) {
            Assert.assertTrue(dashboardPage().isDashboardDisplayed(),
                    "Expected a successful login to reach the Dashboard.");
        } else {
            Assert.assertTrue(loginPageObject().isErrorMessageDisplayed(),
                    "Expected a failed login to show an error message.");
        }
    }

    // ---------------------------------------------------------------
    // Raw DataTable (no transformer)
    // ---------------------------------------------------------------
    @When("I attempt the following login combinations:")
    public void i_attempt_the_following_login_combinations(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            context.addRawLoginAttempt(row.get("username") + ":" + row.get("password"));
        }
    }

    @Then("each raw login attempt should be recorded")
    public void each_raw_login_attempt_should_be_recorded() {
        Assert.assertFalse(context.getRawLoginAttempts().isEmpty(), "No raw DataTable rows were recorded.");
        System.out.println("[ConceptsSteps] raw DataTable rows captured -> " + context.getRawLoginAttempts());
    }

    // ---------------------------------------------------------------
    // Custom DataTable Transformer
    // A step method that declares List<LoginCredentials> as its parameter
    // gets each row auto-converted through this @DataTableType method,
    // instead of handling raw Map<String,String> rows by hand.
    // ---------------------------------------------------------------
    @DataTableType
    public LoginCredentials loginCredentialsEntry(Map<String, String> entry) {
        return new LoginCredentials(entry.get("username"), entry.get("password"));
    }

    @When("I attempt the following login credentials:")
    public void i_attempt_the_following_login_credentials(List<LoginCredentials> credentialsList) {
        credentialsList.forEach(context::addTransformedCredential);
    }

    @Then("each transformed credential should be recorded")
    public void each_transformed_credential_should_be_recorded() {
        Assert.assertFalse(context.getTransformedCredentials().isEmpty(),
                "No rows were converted by the custom @DataTableType transformer.");
        System.out.println("[ConceptsSteps] transformed credentials -> " + context.getTransformedCredentials());
    }

    // ---------------------------------------------------------------
    // Cross-class shared state - writer side (see stepDefinations.CrossClassSteps
    // for the reader side, backed by the SAME injected TestContext instance)
    // ---------------------------------------------------------------
    @Given("I enter the username {string} in one step class")
    public void i_enter_the_username_in_one_step_class(String username) {
        context.setUsername(username);
    }
}
