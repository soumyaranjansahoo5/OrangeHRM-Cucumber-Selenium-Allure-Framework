@dashboard
Feature: OrangeHRM Dashboard
  As a logged in user
  I want to view and navigate from my Dashboard
  So that I can access the different HR modules

  Background:
    Given I am on the OrangeHRM login page
    And I log in with valid credentials

  @smoke @TC03
  Scenario: TC03 - Verify Dashboard is displayed after successful login
    Then I should be redirected to the Dashboard
    And dashboard widgets should be visible

  @regression @TC04
  Scenario Outline: TC04 - Verify navigation from Dashboard to major modules
    When I navigate to the "<module>" module from the main menu
    Then the page title should be "<module>"

    Examples:
      | module       |
      | PIM          |
      | Leave        |
      | Admin        |
      | Recruitment  |
