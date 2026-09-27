@pim
Feature: PIM - Employee Management
  As an HR administrator
  I want to manage employee records
  So that employee data stays accurate and up to date

  Background:
    Given I am on the OrangeHRM login page
    And I log in with valid credentials
    And I navigate to the "PIM" module from the main menu

  @regression @TC05
  Scenario: TC05 - Search for an existing employee and verify the employee is displayed
    When I search for an employee named "Admin"
    Then the employee "Admin" should be displayed in the employee list

  @regression @TC06
  Scenario: TC06 - Add a new employee and verify the employee is created successfully
    When I add a new employee with a randomly generated name
    Then the new employee's personal details page should be displayed

  @regression @TC07
  Scenario: TC07 - Edit employee information and verify the updated information
    When I open the first employee record in the employee list
    And I update the employee's first and last name
    And I save the employee details
    Then the employee details should be updated successfully

  @regression @TC08
  Scenario: TC08 - Delete an employee and verify the employee is removed
    When I add a new employee with a randomly generated name
    And I navigate to the "PIM" module from the main menu
    And I search for the newly added employee
    And I delete the first employee in the employee list
    Then the employee should no longer appear in the employee list
