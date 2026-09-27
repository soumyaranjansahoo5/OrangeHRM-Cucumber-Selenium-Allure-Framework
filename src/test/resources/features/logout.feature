@logout
Feature: Logout
  As a logged in user
  I want to log out of the application
  So that my session is securely ended

  @smoke @TC16
  Scenario: TC16 - Logout successfully and verify the user is redirected to the Login page
    Given I am on the OrangeHRM login page
    And I log in with valid credentials
    When I click the user dropdown and select logout
    Then I should be redirected to the Login page
