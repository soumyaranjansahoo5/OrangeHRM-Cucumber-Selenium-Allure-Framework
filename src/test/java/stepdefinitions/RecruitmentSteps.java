package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.RecruitmentPage;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.TestData;

public class RecruitmentSteps {

    private RecruitmentPage recruitmentPage;
    private String lastFirstName;
    private String lastLastName;

    private RecruitmentPage recruitmentPage() {
        if (recruitmentPage == null) {
            recruitmentPage = new RecruitmentPage(DriverFactory.getDriver(), ConfigReader.getExplicitWait());
        }
        return recruitmentPage;
    }

    @When("I add a new candidate with a randomly generated name")
    public void i_add_a_new_candidate_with_a_randomly_generated_name() {
        lastFirstName = TestData.randomCandidateFirstName();
        lastLastName = TestData.randomCandidateLastName();
        String email = TestData.randomEmail();

        recruitmentPage().clickAddCandidate();
        recruitmentPage().addCandidate(lastFirstName, lastLastName, email);
        Assert.assertTrue(recruitmentPage().isRedirectedToCandidateProfile(),
                "Candidate was not saved successfully.");
    }

    @Then("the candidate should be displayed in the candidate list")
    public void the_candidate_should_be_displayed_in_the_candidate_list() {
        Assert.assertTrue(recruitmentPage().isCandidateInList(lastFirstName, lastLastName),
                "Candidate '" + lastFirstName + " " + lastLastName + "' was not found in the candidate list.");
    }
}
