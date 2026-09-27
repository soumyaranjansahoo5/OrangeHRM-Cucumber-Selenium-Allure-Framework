package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the OrangeHRM Dashboard (landing page after login).
 */
public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dashboardHeader = By.cssSelector(".oxd-topbar-header-breadcrumb h6");
    private final By widgets = By.cssSelector(".oxd-grid-item");

    public DashboardPage(WebDriver driver, int explicitWaitSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSeconds));
    }

    public boolean isDashboardDisplayed() {
        try {
            String headerText = wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader)).getText();
            return headerText.trim().equalsIgnoreCase("Dashboard");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areWidgetsDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader));
        return driver.findElements(widgets).size() > 0;
    }
}
