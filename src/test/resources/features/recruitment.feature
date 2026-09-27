@recruitment
Feature: Recruitment
  As an HR recruiter
  I want to add candidates to the recruitment pipeline
  So that new applicants can be tracked through the hiring process

  Background:
    Given I am on the OrangeHRM login page
    And I log in with valid credentials
    And I navigate to the "Recruitment" module from the main menu

  @regression @TC15
  Scenario: TC15 - Add a candidate and verify the candidate appears in the candidate list
    When I add a new candidate with a randomly generated name
    And I navigate to the "Recruitment" module from the main menu
    Then the candidate should be displayed in the candidate list
