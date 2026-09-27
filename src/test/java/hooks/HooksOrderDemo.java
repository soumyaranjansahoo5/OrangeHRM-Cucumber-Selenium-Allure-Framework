package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import sharedData.TestContext;

import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates Cucumber HOOK ORDERING. Scoped to the "@hooksDemo" tag only
 * (via the value= tag expression on each annotation) so it never runs for
 * -- and never interferes with -- the rest of the suite's existing
 * hooks.Hooks class, which stays untagged and continues to manage the
 * WebDriver lifecycle for every scenario.
 *
 * Confirmed rule: for @Before hooks the LOWEST order value runs first;
 * for @After hooks the HIGHEST order value runs first. The two sets
 * "unwind like a stack" around the scenario body:
 *
 *   Before(order=1) -> Before(order=2) -> Before(order=3)
 *   ... scenario steps run here ...
 *   After(order=3)  -> After(order=2)  -> After(order=1)
 *
 * Every entry is appended to the shared TestContext's hookExecutionLog so
 * stepDefinations.ConceptsSteps can assert the Before-side ordering mid
 * scenario, and the final @After hook (order=1, so it runs LAST) verifies
 * the complete six-entry sequence once the scenario has finished.
 */
public class HooksOrderDemo {

    private final TestContext context;

    public HooksOrderDemo(TestContext context) {
        this.context = context;
    }

    @Before(value = "@hooksDemo", order = 1)
    public void beforeOrder1() {
        context.addHookLog("BEFORE-order-1");
    }

    @Before(value = "@hooksDemo", order = 2)
    public void beforeOrder2() {
        context.addHookLog("BEFORE-order-2");
    }

    @Before(value = "@hooksDemo", order = 3)
    public void beforeOrder3() {
        context.addHookLog("BEFORE-order-3");
    }

    @After(value = "@hooksDemo", order = 3)
    public void afterOrder3() {
        context.addHookLog("AFTER-order-3");
    }

    @After(value = "@hooksDemo", order = 2)
    public void afterOrder2() {
        context.addHookLog("AFTER-order-2");
    }

    @After(value = "@hooksDemo", order = 1)
    public void afterOrder1() {
        List<String> expected = Arrays.asList(
                "BEFORE-order-1", "BEFORE-order-2", "BEFORE-order-3",
                "AFTER-order-3", "AFTER-order-2", "AFTER-order-1"
        );
        List<String> actual = context.getHookExecutionLog();

        System.out.println("[HooksOrderDemo] full execution order -> " + actual);

        if (!actual.equals(expected)) {
            throw new AssertionError(
                    "Hook ordering was WRONG.\nExpected: " + expected + "\nActual:   " + actual);
        }
        System.out.println("[HooksOrderDemo] verified: Before ran ascending, After ran descending.");
    }
}
