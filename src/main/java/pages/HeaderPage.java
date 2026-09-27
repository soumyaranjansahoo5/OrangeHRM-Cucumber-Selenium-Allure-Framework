package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for the top navigation bar shared across every module
 * (breadcrumb title, main menu links, and the user dropdown used to log out).
 */
public class HeaderPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By breadcrumbTitle = By.cssSelector(".oxd-topbar-header-breadcrumb h6");
    private final By mainMenuItems = By.cssSelector(".oxd-main-menu-item--name");
    private final By userDropdown = By.cssSelector(".oxd-userdropdown-tab");
    private final By logoutLink = By.xpath("//a[text()='Logout']");

    public HeaderPage(WebDriver driver, int explicitWaitSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSeconds));
    }

    public String getPageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(breadcrumbTitle)).getText().trim();
    }

    public void navigateToModule(String moduleName) {
        List<WebElement> menuItems = driver.findElements(mainMenuItems);
        for (WebElement item : menuItems) {
            if (item.getText().trim().equalsIgnoreCase(moduleName)) {
                item.click();
                return;
            }
        }
        throw new RuntimeException("Menu item '" + moduleName + "' was not found in the main navigation menu.");
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }
}
