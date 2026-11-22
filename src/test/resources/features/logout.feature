@logout
Feature: Logout
  As a logged-in OrangeHRM user
  I want to sign out
  So that nobody else can use my session

  Background:
    Given the user is logged in as Admin

  @smoke @positive
  Scenario: Logout from the user menu
    When the user opens the user menu
    Then the Logout option should be visible
    When the user clicks Logout
    Then the login page should be displayed

  @regression @security
  Scenario: Dashboard is not reachable after logout
    When the user logs out
    And the user opens the dashboard URL directly
    Then the login page should be displayed
