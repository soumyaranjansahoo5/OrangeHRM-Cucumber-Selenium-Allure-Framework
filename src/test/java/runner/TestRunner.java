package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Entry point for executing all Cucumber feature files via TestNG.
 *
 * Run all tests:            mvn clean test
 * Run only @smoke tests:    mvn test -Dcucumber.filter.tags="@smoke"
 * Run only @regression:     mvn test -Dcucumber.filter.tags="@regression"
 *
 * This class can also be run directly from Eclipse/IntelliJ as a TestNG test.
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-html-report.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        publish = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @org.testng.annotations.DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
