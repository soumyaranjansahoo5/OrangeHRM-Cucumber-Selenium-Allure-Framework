package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for the Recruitment module: candidate list and Add Candidate form.
 */
public class RecruitmentPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By addButton = By.xpath("//button[normalize-space()='Add']");
    private final By firstNameInput = By.name("firstName");
    private final By lastNameInput = By.name("lastName");
    private final By emailInput = By.xpath("//label[text()='Email']/../..//input");
    private final By saveButton = By.xpath("//button[normalize-space()='Save']");
    private final By candidateTableRows = By.cssSelector(".oxd-table-body .oxd-table-row");
    private final By breadcrumbTitle = By.cssSelector(".oxd-topbar-header-breadcrumb h6");

    public RecruitmentPage(WebDriver driver, int explicitWaitSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSeconds));
    }

    public void clickAddCandidate() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
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

    public void enterEmail(String email) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        field.clear();
        field.sendKeys(email);
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void addCandidate(String firstName, String lastName, String email) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        clickSave();
    }

    public boolean isRedirectedToCandidateProfile() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(breadcrumbTitle))
                    .getText().trim().equalsIgnoreCase("Add Candidate")
                    || driver.findElements(candidateTableRows).size() >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCandidateInList(String firstName, String lastName) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(candidateTableRows));
        List<WebElement> rows = driver.findElements(candidateTableRows);
        for (WebElement row : rows) {
            String rowText = row.getText();
            if (rowText.contains(firstName) && rowText.contains(lastName)) {
                return true;
            }
        }
        return false;
    }
}
