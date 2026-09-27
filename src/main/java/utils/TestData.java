package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Generates dynamic/unique test data at runtime so tests such as
 * "add a new employee" or "add a new system user" can be re-run repeatedly
 * without colliding with data created by a previous run.
 */
public class TestData {

    private TestData() {
        // utility class - no instances
    }

    private static String uniqueSuffix() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss"));
    }

    public static String randomEmployeeFirstName() {
        return "Test" + uniqueSuffix();
    }

    public static String randomEmployeeLastName() {
        return "Employee" + uniqueSuffix();
    }

    public static String randomUsername() {
        return "qauser" + uniqueSuffix();
    }

    public static String randomPassword() {
        return "Qa@" + uniqueSuffix();
    }

    public static String randomCandidateFirstName() {
        return "Candidate" + uniqueSuffix();
    }

    public static String randomCandidateLastName() {
        return "Applicant" + uniqueSuffix();
    }

    public static String randomEmail() {
        return "qa_candidate" + uniqueSuffix() + "@example.com";
    }
}
