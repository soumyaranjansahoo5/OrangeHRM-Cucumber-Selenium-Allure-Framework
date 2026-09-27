package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ScreenshotUtil;

/**
 * Cucumber lifecycle hooks that run around every scenario:
 * - @Before: start the WebDriver, maximize the window, and navigate to the app URL.
 * - @After: capture a screenshot on failure and always quit the WebDriver.
 */
public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        DriverFactory.initDriver();
        WebDriver driver = DriverFactory.getDriver();
        driver.get(ConfigReader.getUrl());
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();

        if (driver != null && scenario.isFailed()) {
            byte[] screenshot = ScreenshotUtil.captureScreenshot(driver, scenario.getName());
            scenario.attach(screenshot, "image/png", scenario.getName());
        }

        DriverFactory.quitDriver();
    }
}
