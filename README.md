


# OrangeHRM Web Portal – Selenium Automation Framework
A BDD test automation framework built for the [OrangeHRM demo application](https://opensource-demo.orangehrmlive.com/web/index.php/auth/login), using **Selenium WebDriver**, **Cucumber BDD**, **TestNG**, and the **Page Object Model (POM)**. Built as a QA automation portfolio project demonstrating a professional, maintainable test architecture.

![Java](https://img.shields.io/badge/Java-17-blue)
![Selenium](https://img.shields.io/badge/Selenium-4.18.1-green)
![TestNG](https://img.shields.io/badge/TestNG-7.9.0-orange)
![Cucumber](https://img.shields.io/badge/Cucumber-7.x-brightgreen)
![Maven](https://img.shields.io/badge/Maven-3.9%2B-red)
![Allure](https://img.shields.io/badge/Allure-Report-purple)
![Git](https://img.shields.io/badge/Git-Version%20Control-orange)
![GitHub](https://img.shields.io/badge/GitHub-Repository-black)

A Java-based Selenium WebDriver automation testing framework for the
[OrangeHRM Demo Portal](https://opensource-demo.orangehrmlive.com/).

This project is built using **Selenium WebDriver, Java, Cucumber BDD, TestNG, Maven, Page Object Model (POM), and Allure Reporting**.

The framework is designed to automate important OrangeHRM modules and demonstrate a structured, maintainable, and reusable automation framework.

---

## 📋 Table of Contents

- [Project Overview](#1-project-overview)
- [Technologies Used](#2-technologies-used)
- [Framework Architecture](#3-framework-architecture)
- [Page Object Model (POM) Explanation](#4-page-object-model-pom-explanation)
- [Cucumber BDD Explanation](#5-cucumber-bdd-explanation)
- [Test Scenarios](#6-test-scenarios-16-total)
- [Project Structure](#7-project-structure)
- [Prerequisites](#8-prerequisites)
- [Installation Steps](#9-installation-steps)
- [How to Run All Tests](#10-how-to-run-all-tests)
- [How to Run by Cucumber Tags](#11-how-to-run-by-cucumber-tags)
- [How to Run Smoke Tests](#12-how-to-run-smoke-tests)
- [How to Run Regression Tests](#13-how-to-run-regression-tests)
- [How to Generate / Open Reports](#14-how-to-generate--open-reports)
- [Screenshot-on-Failure](#15-screenshot-on-failure)
- [Running from Eclipse / IntelliJ](#16-running-from-eclipse--intellij)
- [Git / GitHub Instructions](#17-git--github-instructions)
- [Cucumber Concepts Demo](#18-cucumber-concepts-demo-background-hooks-ordering-outline-datatable-transformers-shared-state)
- [Author](#author)

---
---

## 1. Project Overview

This framework automates 16 end-to-end test scenarios covering the core OrangeHRM workflows: login, dashboard navigation, employee (PIM) management, leave management, admin/user management, recruitment, and logout. Tests are written in Gherkin (plain-English BDD syntax) and executed through Cucumber's TestNG runner, with HTML/JSON reporting and automatic screenshot capture on failure.

## 2. Technologies Used

| Category            | Technology                          |
|----------------------|--------------------------------------|
| Language              | Java 11                             |
| Browser automation    | Selenium WebDriver 4.21              |
| BDD framework         | Cucumber JVM 7.18 (Gherkin)          |
| Test runner           | TestNG 7.10 (via cucumber-testng)    |
| Build tool            | Apache Maven                         |
| Driver management     | WebDriverManager (Bonigarcia)        |
| Design pattern        | Page Object Model (POM)              |
| Reporting             | Cucumber HTML / JSON / JUnit + Allure Report |

## 3. Framework Architecture

The framework separates concerns into four distinct layers:

```
Feature Files (Gherkin)  -->  Step Definitions  -->  Page Objects  -->  Selenium WebDriver
                                     |
                                  Hooks (setup/teardown)
                                     |
                              Utils (config, driver, data, screenshots)
```

- **Feature files** describe *what* the application should do, in business-readable language.
- **Step definitions** translate Gherkin steps into Java method calls — no Selenium locators live here.
- **Page Objects** encapsulate all locators and low-level interactions for a single screen.
- **Hooks** manage the WebDriver lifecycle around every scenario.
- **Utils** provide shared, reusable infrastructure (config reading, driver creation, dynamic test data, screenshots).

## 4. Page Object Model (POM) Explanation

Every OrangeHRM screen used by the tests has a corresponding Page Object class under `src/main/java/pages`:

- `LoginPage` — login form and error handling
- `HeaderPage` — shared top navigation bar, main menu, and logout
- `DashboardPage` — dashboard widgets and landing page
- `PIMPage` — employee list search, add/delete entry points
- `EmployeePage` — add/edit employee personal details form
- `LeavePage` — leave list filters and the apply-leave form
- `AdminPage` — system user search and add-user form
- `RecruitmentPage` — add-candidate form and candidate list

Each Page Object exposes **behavior methods** (e.g. `login(username, password)`, `addNewEmployee(firstName, lastName)`) rather than exposing raw WebElements, so step definitions stay clean and readable. All locators are private to their Page Object, in line with POM best practice.

## 5. Cucumber BDD Explanation

Test scenarios are written in Gherkin (`Given / When / Then`) inside `.feature` files. Cucumber matches each step against a Java method annotated with a matching pattern in `src/test/java/stepdefinitions`. This keeps test *intent* (feature files) decoupled from test *implementation* (step definitions + page objects), so non-technical stakeholders can read and review scenarios without touching code.

Tags (`@smoke`, `@regression`, `@login`, `@pim`, `@leave`, `@admin`, `@recruitment`) are used throughout to allow selective test execution.

## 6. Test Scenarios (16 total)

| ID   | Module        | Scenario                                                             | Tags                  |
|------|---------------|-----------------------------------------------------------------------|------------------------|
| TC01 | Login          | Verify successful login with valid credentials                       | `@smoke @login`       |
| TC02 | Login          | Verify login failure with invalid credentials                        | `@regression @login`  |
| TC03 | Dashboard      | Verify Dashboard is displayed after successful login                 | `@smoke @dashboard`   |
| TC04 | Dashboard      | Verify navigation from Dashboard to major modules                    | `@regression @dashboard` |
| TC05 | PIM            | Search for an existing employee and verify the employee is displayed | `@regression @pim`    |
| TC06 | PIM            | Add a new employee and verify the employee is created successfully   | `@regression @pim`    |
| TC07 | PIM            | Edit employee information and verify the updated information         | `@regression @pim`    |
| TC08 | PIM            | Delete an employee and verify the employee is removed                | `@regression @pim`    |
| TC09 | Leave          | Search Leave List using Pending Approval status                      | `@regression @leave`  |
| TC10 | Leave          | Search leave records using Employee Name and Leave Type              | `@regression @leave`  |
| TC11 | Leave          | Verify Reset clears the Leave List filters                           | `@regression @leave`  |
| TC12 | Leave          | Apply for leave and verify the leave request is submitted            | `@smoke @leave`       |
| TC13 | Admin          | Search for an existing system user and verify the result             | `@regression @admin`  |
| TC14 | Admin          | Add a new system user and verify the user is created                 | `@regression @admin`  |
| TC15 | Recruitment    | Add a candidate and verify the candidate appears in the candidate list | `@regression @recruitment` |
| TC16 | Logout         | Logout successfully and verify redirection to the Login page         | `@smoke @logout`      |

## 7. Project Structure

```
OrangeHRM-Cucumber-Selenium
│
├── src
│   ├── main
│   │   └── java
│   │       ├── pages/          # Page Object classes
│   │       └── utils/          # DriverFactory, ConfigReader, ScreenshotUtil, TestData
│   │
│   └── test
│       ├── java
│       │   ├── stepdefinitions/  # Step definition classes
│       │   ├── hooks/            # Cucumber @Before/@After hooks
│       │   └── runner/           # TestRunner (Cucumber + TestNG)
│       │
│       └── resources
│           ├── features/         # .feature files (Gherkin)
│           └── config.properties
│
├── pom.xml
├── testng.xml
└── README.md
```

## 8. Prerequisites

- **Java JDK 11+** installed and on your `PATH`
- **Apache Maven 3.6+** installed and on your `PATH`
- Google Chrome (or Firefox, if you switch `browser` in `config.properties`) installed
- Internet access (WebDriverManager downloads the matching driver binary automatically; no manual chromedriver setup needed)

## 9. Installation Steps

1. Extract/clone the project.
2. Open a terminal in the project root (where `pom.xml` lives).
3. Install dependencies:
   ```
   mvn clean install -DskipTests
   ```
4. Open `src/test/resources/config.properties` and confirm/update:
   ```
   browser=chrome
   url=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
   username=Admin
   password=admin123
   ```
   > The demo site periodically resets its data. If `Admin/admin123` no longer works, update these two values — credentials are never hardcoded in Page Objects or Step Definitions.

## 10. How to Run All Tests

```
mvn clean test
```

This executes all 16 scenarios across all 7 feature files via `runner.TestRunner`.

## 11. How to Run by Cucumber Tags

```
mvn test -Dcucumber.filter.tags="@login"
mvn test -Dcucumber.filter.tags="@pim"
mvn test -Dcucumber.filter.tags="@leave"
mvn test -Dcucumber.filter.tags="@admin"
mvn test -Dcucumber.filter.tags="@recruitment"
```

Tags can also be combined, e.g. `"@pim and @regression"` or `"@smoke or @regression"`.

## 12. How to Run Smoke Tests

```
mvn test -Dcucumber.filter.tags="@smoke"
```

Runs TC01, TC03, TC12, and TC16 — the critical-path checks across login, dashboard, leave, and logout.

## 13. How to Run Regression Tests

```
mvn test -Dcucumber.filter.tags="@regression"
```

Runs the full regression set (TC02, TC04–TC11, TC13–TC15).

## 14. How to Generate / Open Reports

Reports are generated automatically on every run under `target/cucumber-reports/`:

- **HTML report:** `target/cucumber-reports/cucumber-html-report.html` — open directly in a browser.
- **JSON report:** `target/cucumber-reports/cucumber.json` — useful for CI dashboards (e.g. Jenkins Cucumber plugin).
- **JUnit XML report:** `target/cucumber-reports/cucumber.xml` — for CI test-result integration.

### Allure Report

The framework also generates an [Allure](https://allurereport.org) report — a richer, interactive HTML report with per-scenario timelines, step breakdowns, embedded failure screenshots, categorized failures, and trend graphs across runs.

**One-time setup (only needed for the `allure` CLI command, not for Maven):**
Install the Allure commandline so you can open reports locally:
```bash
# macOS
brew install allure

# Windows (scoop)
scoop install allure

# Or download the ZIP from https://github.com/allure-framework/allure2/releases and add its bin/ folder to PATH
```
(You do **not** need this if you only use the `mvn allure:...` goals below — the Maven plugin downloads its own copy of Allure.)

**Run the tests, then generate/view the report:**
```bash
# 1. Run the tests (writes raw results to target/allure-results)
mvn clean test

# 2. Generate a static HTML report and open it in your browser
mvn allure:report
mvn allure:serve

# Alternative, if you installed the Allure CLI yourself:
allure serve target/allure-results
```

`mvn allure:serve` is the quickest option for local use — it builds the report, starts a local web server, and opens it automatically; the server stops when you close the terminal. `mvn allure:report` instead writes a static copy to `target/site/allure-maven-plugin/index.html` that you can zip up, host, or attach to a CI build artifact.

Failure screenshots captured by `ScreenshotUtil` (see below) are attached automatically to the corresponding failed step in the Allure report — no extra wiring needed, since Allure listens to the same Cucumber `scenario.attach()` calls the framework already makes.

## 15. Screenshot-on-Failure

The `Hooks` class's `@After` method checks `scenario.isFailed()`. If a scenario fails, `ScreenshotUtil` captures a PNG screenshot, saves it to `/screenshots` (timestamped and named after the scenario), and attaches it to the Cucumber report via `scenario.attach(...)` so it's visible directly inside the HTML report. The WebDriver session is always quit afterward, pass or fail.

## 16. Running from Eclipse / IntelliJ

1. Import the project as a **Maven project**.
2. Let Maven resolve dependencies (Eclipse: right-click project → Maven → Update Project; IntelliJ: auto-imports on open).
3. Locate `src/test/java/runner/TestRunner.java`.
4. Right-click the file → **Run As → TestNG Test** (Eclipse) or click the green run gutter icon (IntelliJ).
5. Test results appear in the IDE's TestNG results panel; the Cucumber HTML/JSON reports are still written to `target/cucumber-reports/`.

## 17. Git / GitHub Instructions

To publish this project to your own GitHub repository:

```bash
git init
git add .
git commit -m "Initial commit: OrangeHRM Cucumber Selenium framework"
git branch -M main
git remote add origin https://github.com/<your-username>/OrangeHRM-Cucumber-Selenium.git
git push -u origin main
```

Recommended `.gitignore` entries (create a `.gitignore` file if one isn't present):

```
target/
screenshots/
*.class
.idea/
*.iml
.vscode/
```

## 18. Cucumber Concepts Demo (Background, Hooks Ordering, Outline, DataTable, Transformers, Shared State)

A second, self-contained feature — `src/test/resources/features/concepts_demo.feature` (tag `@conceptsDemo`) — was added purely to demonstrate six Cucumber concepts side by side, all exercised against the same login page. It lives in its own packages so it never touches the 16 scenarios above:

```
src
├── main/java/pageObjects/         # LoginPageObject.java  (POM used only by this demo)
└── test/java
    ├── stepDefinations/           # ConceptsSteps.java, CrossClassSteps.java
    ├── hooks/HooksOrderDemo.java  # tag-scoped @Before/@After ordering demo
    ├── sharedData/                # TestContext.java, LoginCredentials.java
    └── cucumberOptions/           # ConceptsTestNGRunner.java, ConceptsJUnitRunner.java
```

| # | Concept | Where it's shown |
|---|---------|-------------------|
| 1 | **Background** | `concepts_demo.feature` — `Given I open the OrangeHRM login page` runs before every scenario in the file. |
| 2 | **Hooks (correct ordering)** | `hooks/HooksOrderDemo.java` — three `@Before(order=1,2,3)` and three `@After(order=1,2,3)`, tagged `@hooksDemo` only. Confirmed rule: `@Before` runs **lowest order first**; `@After` runs **highest order first** — so execution unwinds like a stack: `Before1→Before2→Before3 ... After3→After2→After1`. The final `@After` (order=1, so it runs last) throws if the recorded sequence doesn't match exactly. |
| 3 | **Scenario Outline** | `@outlineDemo` scenario — one `Examples` table drives three full login attempts (success + two failure cases) through the *same* two steps. |
| 4 | **DataTable (raw)** | `@dataTableDemo` scenario — the step takes a plain `io.cucumber.datatable.DataTable` and converts it to `List<Map<String,String>>` by hand. |
| 5 | **Custom Transformer** | `@transformerDemo` scenario — the step instead declares `List<LoginCredentials>` directly; a `@DataTableType` method in `ConceptsSteps` auto-converts every row, so no manual `Map` handling is needed at the call site. |
| 6 | **Cross-class state sharing** | `@sharedDataDemo` scenario — `ConceptsSteps` (Given) writes a username into `sharedData.TestContext`; the completely separate `CrossClassSteps` class (Then) reads it back. Both classes take `TestContext` as a constructor parameter, and Cucumber's PicoContainer DI hands them the *same* instance for the scenario — no statics/singletons involved. Requires the `cucumber-picocontainer` dependency (added to `pom.xml`). |

**Two runners**, both pointed at the same `concepts_demo.feature`, to show both wiring styles side by side:

- `cucumberOptions.ConceptsTestNGRunner` — TestNG-based (`AbstractTestNGCucumberTests`), wired into `testng.xml` as a second `<test>` block, so it runs automatically with `mvn clean test` alongside `runner.TestRunner`.
- `cucumberOptions.ConceptsJUnitRunner` — JUnit4-based (`@RunWith(Cucumber.class)`), run standalone: `mvn test -Dtest=ConceptsJUnitRunner`, or directly from your IDE.

Run just this demo (either runner):

```bash
mvn test -Dtest=ConceptsTestNGRunner
mvn test -Dtest=ConceptsJUnitRunner
```

Run only one concept's scenario by tag:

```bash
mvn test -Dtest=ConceptsTestNGRunner -Dcucumber.filter.tags="@hooksDemo"
```

---


