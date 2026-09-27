@conceptsDemo
Feature: Cucumber Concepts Demo (OrangeHRM Login)
  As someone learning Cucumber's more advanced features
  I want a single feature that exercises Background, Hooks ordering,
  Scenario Outline, DataTable, custom Transformers, and cross-class state
  sharing
  So that I can see each concept working against a real page:
  https://opensource-demo.orangehrmlive.com/web/index.php/auth/login

  Background:
    Given I open the OrangeHRM login page

  # ------------------------------------------------------------------
  # HOOKS: hooks.HooksOrderDemo runs three tagged @Before hooks (order
  # 1,2,3 - ascending) then, after this scenario's steps finish, three
  # tagged @After hooks (order 3,2,1 - descending). The final After hook
  # fails the scenario if the full six-entry order isn't exactly right.
  # ------------------------------------------------------------------
  @hooksDemo
  Scenario: Verify hooks fire in the correct Before/After order
    Then the hooks should have executed in the correct before/after order

  # ------------------------------------------------------------------
  # SCENARIO OUTLINE: the same two steps run once per Examples row, each
  # row producing its own scenario (and therefore its own Background +
  # driver session).
  # ------------------------------------------------------------------
  @outlineDemo
  Scenario Outline: Login attempt with different credentials
    When I attempt to log in with username "<username>" and password "<password>"
    Then I should see the "<expectedResult>" outcome

    Examples:
      | username | password    | expectedResult |
      | Admin    | admin123    | success        |
      | Admin    | wrongPass1  | failure        |
      | badUser  | admin123    | failure        |

  # ------------------------------------------------------------------
  # DATATABLE (raw): the step receives a plain io.cucumber.datatable.DataTable
  # and converts it to List<Map<String,String>> itself.
  # ------------------------------------------------------------------
  @dataTableDemo
  Scenario: Submit multiple login attempts from a raw DataTable
    When I attempt the following login combinations:
      | username | password  |
      | Admin    | admin123  |
      | Admin    | wrong123  |
    Then each raw login attempt should be recorded

  # ------------------------------------------------------------------
  # CUSTOM TRANSFORMER: the step declares List<LoginCredentials> directly;
  # Cucumber calls the @DataTableType method in ConceptsSteps to convert
  # every row automatically - no manual Map handling needed here.
  # ------------------------------------------------------------------
  @transformerDemo
  Scenario: Submit login attempts using a custom DataTable transformer
    When I attempt the following login credentials:
      | username | password  |
      | Admin    | admin123  |
      | Admin    | wrong123  |
    Then each transformed credential should be recorded

  # ------------------------------------------------------------------
  # CROSS-CLASS STATE SHARING: the Given runs in ConceptsSteps, the Then
  # runs in the separate CrossClassSteps class - both backed by the SAME
  # injected sharedData.TestContext instance for this scenario.
  # ------------------------------------------------------------------
  @sharedDataDemo
  Scenario: Share state between two different step-definition classes
    Given I enter the username "Admin" in one step class
    Then another step class should see the shared username "Admin"
