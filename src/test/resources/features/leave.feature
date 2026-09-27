@leave
Feature: Leave Management
  As an employee or HR administrator
  I want to search, filter and apply for leave
  So that leave records are tracked accurately

  Background:
    Given I am on the OrangeHRM login page
    And I log in with valid credentials
    And I navigate to the "Leave" module from the main menu

  @regression @TC09
  Scenario: TC09 - Search Leave List using Pending Approval status
    When I open the Leave List page
    And I filter the leave list by status "Pending Approval"
    And I click the search button on the leave list
    Then leave records matching the filter should be displayed

  @regression @TC10
  Scenario: TC10 - Search leave records using Employee Name and Leave Type
    When I open the Leave List page
    And I filter the leave list by employee "Admin" and leave type "CAN - Personal"
    And I click the search button on the leave list
    Then leave records matching the filter should be displayed

  @regression @TC11
  Scenario: TC11 - Verify Reset clears the Leave List filters
    When I open the Leave List page
    And I filter the leave list by employee "Admin" and leave type "CAN - Personal"
    And I click the reset button on the leave list
    Then the leave list filters should be cleared

  @smoke @TC12
  Scenario: TC12 - Apply for leave and verify the leave request is submitted
    When I open the Apply Leave page
    And I select leave type "CAN - Personal"
    And I enter valid from and to dates for the leave request
    And I click the apply button
    Then the leave request should be submitted successfully
