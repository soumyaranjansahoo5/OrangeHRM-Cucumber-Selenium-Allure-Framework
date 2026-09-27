package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.EmployeePage;
import pages.PIMPage;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.TestData;

public class EmployeeSteps {

    private PIMPage pimPage;
    private EmployeePage employeePage;

    private String lastFirstName;
    private String lastLastName;

    private PIMPage pimPage() {
        if (pimPage == null) {
            pimPage = new PIMPage(DriverFactory.getDriver(), ConfigReader.getExplicitWait());
        }
        return pimPage;
    }

    private EmployeePage employeePage() {
        if (employeePage == null) {
            employeePage = new EmployeePage(DriverFactory.getDriver(), ConfigReader.getExplicitWait());
        }
        return employeePage;
    }

    @When("I search for an employee named {string}")
    public void i_search_for_an_employee_named(String employeeName) {
        pimPage().searchEmployeeByName(employeeName);
    }

    @Then("the employee {string} should be displayed in the employee list")
    public void the_employee_should_be_displayed_in_the_employee_list(String employeeName) {
        Assert.assertTrue(pimPage().isEmployeeInResults(employeeName),
                "Employee '" + employeeName + "' was not found in the employee list.");
    }

    @When("I add a new employee with a randomly generated name")
    public void i_add_a_new_employee_with_a_randomly_generated_name() {
        lastFirstName = TestData.randomEmployeeFirstName();
        lastLastName = TestData.randomEmployeeLastName();

        pimPage().clickAddEmployee();
        employeePage().addNewEmployee(lastFirstName, lastLastName);
    }

    @Then("the new employee's personal details page should be displayed")
    public void the_new_employee_s_personal_details_page_should_be_displayed() {
        Assert.assertTrue(employeePage().isPersonalDetailsPageDisplayed(),
                "Personal Details page was not displayed after adding a new employee.");
    }

    @When("I open the first employee record in the employee list")
    public void i_open_the_first_employee_record_in_the_employee_list() {
        pimPage().clickFirstEmployeeInList();
    }

    @And("I update the employee's first and last name")
    public void i_update_the_employee_s_first_and_last_name() {
        lastFirstName = TestData.randomEmployeeFirstName();
        lastLastName = TestData.randomEmployeeLastName();

        employeePage().updateFirstName(lastFirstName);
        employeePage().updateLastName(lastLastName);
    }

    @And("I save the employee details")
    public void i_save_the_employee_details() {
        employeePage().saveEmployeeDetails();
    }

    @Then("the employee details should be updated successfully")
    public void the_employee_details_should_be_updated_successfully() {
        Assert.assertTrue(employeePage().isPersonalDetailsPageDisplayed(),
                "Personal Details page was not displayed after updating the employee.");
    }

    @And("I search for the newly added employee")
    public void i_search_for_the_newly_added_employee() {
        pimPage().searchEmployeeByName(lastFirstName);
    }

    @And("I delete the first employee in the employee list")
    public void i_delete_the_first_employee_in_the_employee_list() {
        pimPage().deleteFirstEmployeeInList();
    }

    @Then("the employee should no longer appear in the employee list")
    public void the_employee_should_no_longer_appear_in_the_employee_list() {
        pimPage().searchEmployeeByName(lastFirstName);
        Assert.assertFalse(pimPage().isEmployeeInResults(lastFirstName),
                "Deleted employee '" + lastFirstName + "' still appears in the employee list.");
    }
}
