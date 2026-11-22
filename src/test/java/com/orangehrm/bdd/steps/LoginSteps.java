package com.orangehrm.bdd.steps;

import com.orangehrm.bdd.config.ConfigReader;
import com.orangehrm.bdd.pages.DashboardPage;
import com.orangehrm.bdd.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {

    private LoginPage loginPage() {
        return new LoginPage();
    }

    @Given("the user is on the OrangeHRM login page")
    public void theUserIsOnTheLoginPage() {
        LoginPage page = loginPage();
        page.open();
        assertTrue(page.isDisplayed(), "Login page should show the Login heading, username and password");
    }

    @Given("the user is logged in as Admin")
    public void theUserIsLoggedInAsAdmin() {
        theUserIsOnTheLoginPage();
        loginPage().loginAs(ConfigReader.get("username"), ConfigReader.get("password"));
        assertTrue(new DashboardPage().isDisplayed(), "Dashboard should be shown after logging in");
    }

    @When("the user logs in with valid Admin credentials")
    public void theUserLogsInWithValidAdminCredentials() {
        loginPage().loginAs(ConfigReader.get("username"), ConfigReader.get("password"));
    }

    @When("the user logs in with username {string} and password {string}")
    public void theUserLogsInWith(String username, String password) {
        LoginPage page = loginPage();
        if (!username.isEmpty()) page.enterUsername(username);
        if (!password.isEmpty()) page.enterPassword(password);
        page.clickLogin();
    }

    @When("the user clicks Login without entering credentials")
    public void theUserClicksLoginWithoutCredentials() {
        loginPage().clickLogin();
    }

    @Then("the dashboard should be displayed")
    public void theDashboardShouldBeDisplayed() {
        assertTrue(new DashboardPage().isDisplayed(), "Expected the Dashboard page with side menu");
    }

    @Then("the error message {string} should be displayed")
    public void theErrorMessageShouldBeDisplayed(String expected) {
        assertEquals(expected, loginPage().alertMessage());
    }

    @Then("the user should stay on the login page")
    public void theUserShouldStayOnTheLoginPage() {
        assertTrue(loginPage().isDisplayed(), "User should still be on the login page");
    }

    @Then("the username field should show {string}")
    public void theUsernameFieldShouldShow(String expected) {
        assertEquals(expected, loginPage().usernameError());
    }

    @Then("the password field should show {string}")
    public void thePasswordFieldShouldShow(String expected) {
        assertEquals(expected, loginPage().passwordError());
    }
}
