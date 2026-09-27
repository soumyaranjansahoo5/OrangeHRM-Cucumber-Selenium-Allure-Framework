@login
Feature: OrangeHRM Login
  As a user of the OrangeHRM application
  I want to log in with my credentials
  So that I can access my HR dashboard

  @smoke @TC01
  Scenario: TC01 - Verify successful login with valid credentials
    Given I am on the OrangeHRM login page
    When I enter a valid username
    And I enter a valid password
    And I click the login button
    Then I should be redirected to the Dashboard

  @regression @TC02
  Scenario: TC02 - Verify login failure with invalid credentials
    Given I am on the OrangeHRM login page
    When I enter an invalid username "wrongUser"
    And I enter an invalid password "wrongPass"
    And I click the login button
    Then I should see an "Invalid credentials" error message
