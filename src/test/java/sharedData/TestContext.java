package sharedData;

import java.util.ArrayList;
import java.util.List;

/**
 * Instantiated once per scenario by Cucumber's built-in PicoContainer DI and
 * injected into every glue class whose constructor declares a TestContext
 * parameter. Because PicoContainer hands out the SAME instance to every
 * class within one scenario, this is what makes state sharing between two
 * different step-definition classes possible (see stepDefinations.ConceptsSteps
 * and stepDefinations.CrossClassSteps), and lets hooks.HooksOrderDemo record
 * hook-execution order into an object the step definitions can also read.
 *
 * Requires the io.cucumber:cucumber-picocontainer dependency (added to pom.xml)
 * -- without it, Cucumber falls back to no-arg-constructor-only instantiation
 * and this constructor-injection pattern will fail to wire up.
 */
public class TestContext {

    private String username;

    private final List<String> hookExecutionLog = new ArrayList<>();
    private final List<String> rawLoginAttempts = new ArrayList<>();
    private final List<LoginCredentials> transformedCredentials = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addHookLog(String entry) {
        hookExecutionLog.add(entry);
    }

    public List<String> getHookExecutionLog() {
        return hookExecutionLog;
    }

    public void addRawLoginAttempt(String attempt) {
        rawLoginAttempts.add(attempt);
    }

    public List<String> getRawLoginAttempts() {
        return rawLoginAttempts;
    }

    public void addTransformedCredential(LoginCredentials credentials) {
        transformedCredentials.add(credentials);
    }

    public List<LoginCredentials> getTransformedCredentials() {
        return transformedCredentials;
    }
}
