# OrangeHRM BDD Cucumber — Login & Logout

BDD UI tests for the public [OrangeHRM demo](https://opensource-demo.orangehrmlive.com/web/index.php/auth/login),
written in Gherkin and run with Cucumber, Selenium WebDriver and JUnit 5.

| Tool | Version | Role |
|---|---|---|
| Java | 17+ | Language |
| Maven (wrapper included) | 3.9.9 | Build and test runner |
| Selenium WebDriver | 4.27.0 | Browser automation (Selenium Manager downloads the driver) |
| Cucumber | 7.20.1 | BDD engine |
| JUnit Platform Suite | 1.11.3 | Launches Cucumber |

## Project structure

```
bddcucumber/
├── pom.xml
├── mvnw, mvnw.cmd, .mvn/                     # Maven wrapper: no Maven install needed
└── src/test/
    ├── java/com/orangehrm/bdd/
    │   ├── runner/TestRunner.java            # JUnit suite: runs everything in features/
    │   ├── hooks/Hooks.java                  # new browser per scenario; screenshot on failure
    │   ├── steps/LoginSteps.java             # login step definitions
    │   ├── steps/LogoutSteps.java            # logout step definitions
    │   ├── pages/BasePage.java               # explicit-wait helpers
    │   ├── pages/LoginPage.java              # login page locators + actions
    │   ├── pages/DashboardPage.java          # dashboard + user menu / logout
    │   ├── driver/DriverManager.java         # Chrome / Firefox / Edge, one per thread
    │   └── config/ConfigReader.java          # config.properties, overridable with -D
    └── resources/
        ├── features/login.feature
        ├── features/logout.feature
        ├── config.properties                 # URL, browser, demo credentials, timeouts
        └── junit-platform.properties         # Cucumber glue, default tags, report plugins
```

## Test scenarios

**login.feature** (Background: user is on the login page)

| Scenario | Tags | Expected |
|---|---|---|
| Successful login with valid credentials | @smoke @positive | Dashboard shown |
| Invalid credentials: wrong password / wrong username / both wrong | @regression @negative | "Invalid credentials" alert, still on login page |
| Both fields empty | @regression @validation | "Required" under username and password |
| Password empty | @regression @validation | "Required" under password |
| Username empty | @regression @validation | "Required" under username |

**logout.feature** (Background: user is logged in as Admin)

| Scenario | Tags | Expected |
|---|---|---|
| Logout from the user menu | @smoke @positive | Logout option visible, then login page shown |
| Dashboard is not reachable after logout | @regression @security | Opening the dashboard URL redirects to login |

Total: **9 test runs**, 2 smoke and 7 regression.

## Running

Requires Java 17+ and Chrome (or Firefox/Edge). Use `./mvnw` (macOS/Linux) or `mvnw.cmd` (Windows);
it downloads Maven on first use.

```bash
./mvnw test                                    # all scenarios
./mvnw test -Dheadless=true                    # no browser window (CI)
./mvnw test -Dcucumber.filter.tags="@smoke"    # smoke only
./mvnw test -Dcucumber.filter.tags="@logout"   # one feature
./mvnw test -Dbrowser=firefox                  # chrome | firefox | edge
```

Any key in `config.properties` can be overridden the same way, e.g. `-Dexplicit.wait=30`.

## Reports

| Report | Location |
|---|---|
| Cucumber HTML (with failure screenshots) | `target/cucumber-reports/cucumber.html` |
| Cucumber JSON (for CI plugins) | `target/cucumber-reports/cucumber.json` |
| JUnit XML (for Jenkins `junit` step) | `target/cucumber-reports/cucumber.xml` |

```bash
open target/cucumber-reports/cucumber.html
```

## Design notes

- **Fresh browser per scenario** (`@Before` / `@After`), so no session leaks between scenarios.
- **Explicit waits only** (no implicit wait), so waits never stack and failures are fast and clear.
- **Assertions check real outcomes:** exact error text, the URL, and visible elements, not just
  whether an element exists in the DOM.
- **Tags live in `junit-platform.properties`**, not in runner annotations, so `-Dcucumber.filter.tags`
  on the command line overrides the default (`not @wip`).
- The demo credentials (`Admin` / `admin123`) are published on the OrangeHRM demo page.
  For a real application, pass credentials with `-Dusername=... -Dpassword=...` instead of committing them.

## Adding a feature

1. Add `src/test/resources/features/<name>.feature`.
2. Add step definitions in `src/test/java/com/orangehrm/bdd/steps/`.
3. Add a page object in `src/test/java/com/orangehrm/bdd/pages/` extending `BasePage`.
