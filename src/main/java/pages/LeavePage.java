package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for the Leave module: Leave List search/filter screen and the
 * Apply Leave form.
 */
public class LeavePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By leaveListMenuItem = By.xpath("//a[text()='Leave List']");
    private final By applyLeaveMenuItem = By.xpath("//a[text()='Apply']");
    private final By employeeNameFilter = By.xpath("//label[text()='Employee Name']/../..//input");
    private final By leaveTypeDropdown = By.xpath("//label[text()='Leave Type']/../..//div[contains(@class,'oxd-select-text')]");
    private final By showLeaveWithStatusDropdown = By.xpath("(//div[contains(@class,'oxd-select-text')])[1]");
    private final By dropdownOption = By.cssSelector(".oxd-select-dropdown .oxd-select-option");
    private final By searchButton = By.cssSelector("button[type='submit']");
    private final By resetButton = By.cssSelector("button[type='reset']");
    private final By leaveTableRows = By.cssSelector(".oxd-table-body .oxd-table-row");
    private final By noRecordsFound = By.xpath("//span[text()='No Records Found']");

    // Apply Leave form
    private final By applyLeaveTypeDropdown = By.xpath("//label[text()='Leave Type']/../..//div[contains(@class,'oxd-select-text')]");
    private final By fromDateInput = By.xpath("(//label[text()='From Date']/../..//input)[1]");
    private final By toDateInput = By.xpath("(//label[text()='To Date']/../..//input)[1]");
    private final By applyButton = By.xpath("//button[normalize-space()='Apply']");
    private final By applySuccessMessage = By.cssSelector(".oxd-toast-content--success, .oxd-topbar-header-breadcrumb h6");

    public LeavePage(WebDriver driver, int explicitWaitSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSeconds));
    }

    public void openLeaveList() {
        wait.until(ExpectedConditions.elementToBeClickable(leaveListMenuItem)).click();
    }

    public void openApplyLeaveForm() {
        wait.until(ExpectedConditions.elementToBeClickable(applyLeaveMenuItem)).click();
    }

    public void filterByStatus(String status) {
        wait.until(ExpectedConditions.elementToBeClickable(showLeaveWithStatusDropdown)).click();
        selectDropdownOptionByText(status);
    }

    public void filterByEmployeeNameAndLeaveType(String employeeName, String leaveType) {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameFilter));
        nameField.clear();
        nameField.sendKeys(employeeName);

        try {
            By suggestion = By.cssSelector(".oxd-autocomplete-dropdown-option");
            wait.until(ExpectedConditions.visibilityOfElementLocated(suggestion)).click();
        } catch (Exception ignored) {
            // no matching employee suggestion - continue with typed value
        }

        wait.until(ExpectedConditions.elementToBeClickable(leaveTypeDropdown)).click();
        selectDropdownOptionByText(leaveType);
    }

    private void selectDropdownOptionByText(String text) {
        List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dropdownOption));
        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(text)) {
                option.click();
                return;
            }
        }
        throw new RuntimeException("Dropdown option '" + text + "' was not found.");
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void clickReset() {
        wait.until(ExpectedConditions.elementToBeClickable(resetButton)).click();
    }

    public boolean areResultsDisplayed() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(leaveTableRows),
                ExpectedConditions.visibilityOfElementLocated(noRecordsFound)));
        return driver.findElements(leaveTableRows).size() > 0;
    }

    public boolean isFilterCleared() {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameFilter));
        return nameField.getAttribute("value").isEmpty();
    }

    public void selectLeaveType(String leaveType) {
        wait.until(ExpectedConditions.elementToBeClickable(applyLeaveTypeDropdown)).click();
        selectDropdownOptionByText(leaveType);
    }

    public void enterFromDate(String date) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(fromDateInput));
        field.clear();
        field.sendKeys(date);
    }

    public void enterToDate(String date) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(toDateInput));
        field.clear();
        field.sendKeys(date);
    }

    public void clickApply() {
        wait.until(ExpectedConditions.elementToBeClickable(applyButton)).click();
    }

    public boolean isLeaveApplicationSubmitted() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(applySuccessMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
