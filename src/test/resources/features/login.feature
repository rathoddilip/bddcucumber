@login
Feature: Login
  As an OrangeHRM user
  I want to sign in with my username and password
  So that I can use the HR system

  Background:
    Given the user is on the OrangeHRM login page

  @smoke @positive
  Scenario: Successful login with valid credentials
    When the user logs in with valid Admin credentials
    Then the dashboard should be displayed

  @regression @negative
  Scenario Outline: Login fails with invalid credentials - <case>
    When the user logs in with username "<username>" and password "<password>"
    Then the error message "Invalid credentials" should be displayed
    And the user should stay on the login page

    Examples:
      | case           | username  | password |
      | wrong password | Admin     | wrong123 |
      | wrong username | NoSuchUsr | admin123 |
      | both wrong     | NoSuchUsr | wrong123 |

  @regression @validation
  Scenario: Both fields are required
    When the user clicks Login without entering credentials
    Then the username field should show "Required"
    And the password field should show "Required"
    And the user should stay on the login page

  @regression @validation
  Scenario: Password is required
    When the user logs in with username "Admin" and password ""
    Then the password field should show "Required"
    And the user should stay on the login page

  @regression @validation
  Scenario: Username is required
    When the user logs in with username "" and password "admin123"
    Then the username field should show "Required"
    And the user should stay on the login page
