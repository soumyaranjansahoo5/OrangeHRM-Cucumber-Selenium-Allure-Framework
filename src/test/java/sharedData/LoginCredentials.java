package sharedData;

/**
 * Plain data holder that a custom Cucumber @DataTableType transformer
 * converts each DataTable row into (see stepDefinations.ConceptsSteps).
 */
public class LoginCredentials {

    private final String username;
    private final String password;

    public LoginCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginCredentials{username='" + username + "', password='***'}";
    }
}
