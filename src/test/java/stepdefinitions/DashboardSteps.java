package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.DashboardPage;
import pages.HeaderPage;
import utils.ConfigReader;
import utils.DriverFactory;

public class DashboardSteps {

    private DashboardPage dashboardPage;
    private HeaderPage headerPage;

    private DashboardPage dashboardPage() {
        if (dashboardPage == null) {
            dashboardPage = new DashboardPage(DriverFactory.getDriver(), ConfigReader.getExplicitWait());
        }
        return dashboardPage;
    }

    private HeaderPage headerPage() {
        if (headerPage == null) {
            headerPage = new HeaderPage(DriverFactory.getDriver(), ConfigReader.getExplicitWait());
        }
        return headerPage;
    }

    @And("dashboard widgets should be visible")
    public void dashboard_widgets_should_be_visible() {
        Assert.assertTrue(dashboardPage().areWidgetsDisplayed(), "Dashboard widgets were not visible.");
    }

    @When("I navigate to the {string} module from the main menu")
    public void i_navigate_to_the_module_from_the_main_menu(String module) {
        headerPage().navigateToModule(module);
    }

    @Then("the page title should be {string}")
    public void the_page_title_should_be(String expectedTitle) {
        Assert.assertEquals(headerPage().getPageTitle(), expectedTitle,
                "Page title did not match after navigating to module: " + expectedTitle);
    }
}
