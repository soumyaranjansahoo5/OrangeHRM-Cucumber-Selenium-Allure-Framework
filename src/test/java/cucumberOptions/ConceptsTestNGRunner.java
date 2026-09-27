package cucumberOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Runner #1 of 2: TestNG-based, scoped ONLY to concepts_demo.feature so it
 * runs independently of runner.TestRunner (which drives the rest of the
 * OrangeHRM suite untouched).
 *
 * Run directly:            mvn test -Dtest=ConceptsTestNGRunner
 * Run everything (both suites via testng.xml):  mvn clean test
 */
@CucumberOptions(
        features = "src/test/resources/features/concepts_demo.feature",
        glue = {"stepDefinations", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/concepts-demo-testng-report.html",
                "json:target/cucumber-reports/concepts-demo-testng.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        publish = false
)
public class ConceptsTestNGRunner extends AbstractTestNGCucumberTests {

    @Override
    @org.testng.annotations.DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
