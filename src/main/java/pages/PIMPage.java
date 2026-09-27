package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for the PIM module's Employee List / search screen.
 */
public class PIMPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By employeeNameSearchInput = By.xpath("(//label[text()='Employee Name']/../..//input)[1]");
    private final By searchButton = By.cssSelector("button[type='submit']");
    private final By resetButton = By.cssSelector("button[type='reset']");
    private final By addEmployeeButton = By.xpath("//button[normalize-space()='Add']");
    private final By employeeTableRows = By.cssSelector(".oxd-table-body .oxd-table-row");
    private final By recordsFoundText = By.cssSelector(".orangehrm-horizontal-padding.orangehrm-vertical-padding span");
    private final By deleteIconInFirstRow = By.xpath("(//div[@class='oxd-table-row oxd-table-row--with-border']//button[contains(@class,'oxd-icon-button')][2])[1]");
    private final By deleteConfirmButton = By.xpath("//button[normalize-space()='Yes, Delete']");
    private final By noRecordsFound = By.xpath("//span[text()='No Records Found']");

    public PIMPage(WebDriver driver, int explicitWaitSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSeconds));
    }

    public void searchEmployeeByName(String employeeName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameSearchInput));
        input.clear();
        input.sendKeys(employeeName);

        // Wait for and select the matching autocomplete suggestion, if the demo
        // instance already has an employee with this name.
        try {
            By suggestion = By.cssSelector(".oxd-autocomplete-dropdown-option");
            wait.until(ExpectedConditions.visibilityOfElementLocated(suggestion)).click();
        } catch (Exception ignored) {
            // no autocomplete suggestion appeared - continue with the typed text as-is
        }

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public boolean isEmployeeInResults(String employeeName) {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(employeeTableRows),
                ExpectedConditions.visibilityOfElementLocated(noRecordsFound)));

        List<WebElement> rows = driver.findElements(employeeTableRows);
        for (WebElement row : rows) {
            if (row.getText().contains(employeeName)) {
                return true;
            }
        }
        return false;
    }

    public void clickAddEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeButton)).click();
    }

    public void clickResetFilters() {
        wait.until(ExpectedConditions.elementToBeClickable(resetButton)).click();
    }

    public int getResultRowCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameSearchInput));
        return driver.findElements(employeeTableRows).size();
    }

    public void clickFirstEmployeeInList() {
        List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(employeeTableRows));
        if (rows.isEmpty()) {
            throw new RuntimeException("No employees found in the Employee List to select.");
        }
        rows.get(0).click();
    }

    public void deleteFirstEmployeeInList() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteIconInFirstRow)).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteConfirmButton)).click();
    }

    public boolean isRecordsFoundTextDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(recordsFoundText)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
