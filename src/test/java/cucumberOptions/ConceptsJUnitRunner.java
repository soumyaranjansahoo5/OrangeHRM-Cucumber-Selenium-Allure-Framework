package cucumberOptions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Runner #2 of 2: JUnit4-based, wired to the SAME concepts_demo.feature as
 * ConceptsTestNGRunner - shown side by side to demonstrate the alternative
 * way to run Cucumber glue (JUnit's @RunWith(Cucumber.class) instead of
 * TestNG's AbstractTestNGCucumberTests).
 *
 * Run directly (IDE):  right-click -> Run as JUnit test
 * Run via Maven:       mvn test -Dtest=ConceptsJUnitRunner
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/concepts_demo.feature",
        glue = {"stepDefinations", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/concepts-demo-junit-report.html",
                "json:target/cucumber-reports/concepts-demo-junit.json"
        },
        monochrome = true,
        publish = false
)
public class ConceptsJUnitRunner {
    // Intentionally empty - the annotations above wire everything up.
}
