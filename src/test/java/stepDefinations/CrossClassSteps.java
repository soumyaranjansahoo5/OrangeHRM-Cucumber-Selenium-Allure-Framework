package stepDefinations;

import io.cucumber.java.en.Then;
import org.testng.Assert;
import sharedData.TestContext;

/**
 * A SEPARATE step-definition class from ConceptsSteps. Cucumber's
 * PicoContainer DI hands this class the exact same TestContext instance
 * that ConceptsSteps received for the same scenario, so whatever
 * ConceptsSteps wrote into it (e.g. the username, in
 * i_enter_the_username_in_one_step_class) is visible here without any
 * static fields or singletons.
 */
public class CrossClassSteps {

    private final TestContext context;

    public CrossClassSteps(TestContext context) {
        this.context = context;
    }

    @Then("another step class should see the shared username {string}")
    public void another_step_class_should_see_the_shared_username(String expectedUsername) {
        Assert.assertEquals(context.getUsername(), expectedUsername,
                "Username set in ConceptsSteps was not visible in CrossClassSteps - " +
                        "the shared TestContext instance is not being injected correctly.");
        System.out.println("[CrossClassSteps] confirms shared username -> " + context.getUsername());
    }
}
