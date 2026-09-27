package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Captures screenshots and stores them under the /screenshots directory.
 * Used by the Cucumber Hooks class to attach evidence when a scenario fails.
 */
public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "screenshots";

    private ScreenshotUtil() {
        // utility class - no instances
    }

    /**
     * Takes a screenshot and saves it to disk.
     *
     * @param driver     active WebDriver instance
     * @param scenarioName name of the failing scenario, used in the file name
     * @return the byte array of the screenshot (used for Cucumber's embed API)
     */
    public static byte[] captureScreenshot(WebDriver driver, String scenarioName) {
        byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        try {
            Files.createDirectories(Paths.get(SCREENSHOT_DIR));

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String safeName = scenarioName.replaceAll("[^a-zA-Z0-9-_]", "_");
            File destination = new File(SCREENSHOT_DIR + File.separator + safeName + "_" + timestamp + ".png");

            Files.write(destination.toPath(), screenshotBytes);
        } catch (IOException e) {
            System.err.println("Failed to save screenshot for scenario '" + scenarioName + "': " + e.getMessage());
        }

        return screenshotBytes;
    }
}
