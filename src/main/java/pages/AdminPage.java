package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for Admin > User Management (search users, add a new system user).
 */
public class AdminPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameFilter = By.xpath("(//label[text()='Username']/../..//input)[1]");
    private final By searchButton = By.cssSelector("button[type='submit']");
    private final By addButton = By.xpath("//button[normalize-space()='Add']");
    private final By userTableRows = By.cssSelector(".oxd-table-body .oxd-table-row");
    private final By noRecordsFound = By.xpath("//span[text()='No Records Found']");

    // Add User form
    private final By userRoleDropdown = By.xpath("(//div[contains(@class,'oxd-select-text')])[1]");
    private final By employeeNameInput = By.xpath("//label[text()='Employee Name']/../..//input");
    private final By statusDropdown = By.xpath("(//div[contains(@class,'oxd-select-text')])[2]");
    private final By newUsernameInput = By.xpath("(//label[text()='Username']/../..//input)[1]");
    private final By passwordInput = By.xpath("//label[text()='Password']/../..//input");
    private final By confirmPasswordInput = By.xpath("//label[text()='Confirm Password']/../..//input");
    private final By dropdownOption = By.cssSelector(".oxd-select-dropdown .oxd-select-option");
    private final By saveButton = By.xpath("//button[normalize-space()='Save']");

    public AdminPage(WebDriver driver, int explicitWaitSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSeconds));
    }

    public void searchUserByUsername(String username) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameFilter));
        field.clear();
        field.sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public boolean isUserInResults(String username) {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(userTableRows),
                ExpectedConditions.visibilityOfElementLocated(noRecordsFound)));

        List<WebElement> rows = driver.findElements(userTableRows);
        for (WebElement row : rows) {
            if (row.getText().contains(username)) {
                return true;
            }
        }
        return false;
    }

    public void clickAddUser() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    private void selectDropdownOptionByText(By dropdownLocator, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator)).click();
        List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dropdownOption));
        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(text)) {
                option.click();
                return;
            }
        }
        throw new RuntimeException("Dropdown option '" + text + "' was not found.");
    }

    public void selectUserRole(String role) {
        selectDropdownOptionByText(userRoleDropdown, role);
    }

    public void enterEmployeeName(String employeeName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
        field.clear();
        field.sendKeys(employeeName);

        try {
            By suggestion = By.cssSelector(".oxd-autocomplete-dropdown-option");
            wait.until(ExpectedConditions.visibilityOfElementLocated(suggestion)).click();
        } catch (Exception ignored) {
            // no autocomplete suggestion appeared for this employee name
        }
    }

    public void selectStatus(String status) {
        selectDropdownOptionByText(statusDropdown, status);
    }

    public void enterNewUsername(String username) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(newUsernameInput));
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        field.clear();
        field.sendKeys(password);
    }

    public void enterConfirmPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordInput));
        field.clear();
        field.sendKeys(password);
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void addNewSystemUser(String role, String employeeName, String status, String username, String password) {
        selectUserRole(role);
        enterEmployeeName(employeeName);
        selectStatus(status);
        enterNewUsername(username);
        enterPassword(password);
        enterConfirmPassword(password);
        clickSave();
    }
}
