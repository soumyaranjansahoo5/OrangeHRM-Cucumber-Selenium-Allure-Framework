@admin
Feature: Admin - User Management
  As a system administrator
  I want to manage system users
  So that access to OrangeHRM is properly controlled

  Background:
    Given I am on the OrangeHRM login page
    And I log in with valid credentials
    And I navigate to the "Admin" module from the main menu

  @regression @TC13
  Scenario: TC13 - Search for an existing system user and verify the result
    When I search for the system user "Admin"
    Then the user "Admin" should be displayed in the user list

  @regression @TC14
  Scenario: TC14 - Add a new system user and verify the user is created
    When I add a new system user with a randomly generated username
    Then the newly created user should be displayed in the user list
