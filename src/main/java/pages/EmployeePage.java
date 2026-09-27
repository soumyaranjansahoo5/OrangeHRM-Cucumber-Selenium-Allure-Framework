package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the Add Employee / Employee Personal Details screen.
 */
public class EmployeePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By firstNameInput = By.name("firstName");
    private final By lastNameInput = By.name("lastName");
    private final By employeeIdInput = By.xpath("//label[text()='Employee Id']/../..//input");
    private final By saveButton = By.xpath("//button[normalize-space()='Save']");
    private final By employeeFullNameHeader = By.cssSelector(".oxd-topbar-header-breadcrumb h6");
    private final By personalDetailsHeader = By.xpath("//h6[text()='Personal Details']");
    private final By editFirstNameInput = By.xpath("//label[text()='First Name']/../..//input");
    private final By editLastNameInput = By.xpath("//label[text()='Last Name']/../..//input");

    public EmployeePage(WebDriver driver, int explicitWaitSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSeconds));
    }

    public void enterFirstName(String firstName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
        field.clear();
        field.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput));
        field.clear();
        field.sendKeys(lastName);
    }

    public void addNewEmployee(String firstName, String lastName) {
        enterFirstName(firstName);
        enterLastName(lastName);
        clickSave();
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public boolean isPersonalDetailsPageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(personalDetailsHeader)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void updateFirstName(String newFirstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(personalDetailsHeader));
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(editFirstNameInput));
        field.clear();
        field.sendKeys(newFirstName);
    }

    public void updateLastName(String newLastName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(editLastNameInput));
        field.clear();
        field.sendKeys(newLastName);
    }

    public void saveEmployeeDetails() {
        clickSave();
    }
}
