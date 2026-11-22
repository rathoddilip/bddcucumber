package com.orangehrm.bdd.steps;

import com.orangehrm.bdd.config.ConfigReader;
import com.orangehrm.bdd.pages.DashboardPage;
import com.orangehrm.bdd.pages.LoginPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogoutSteps {

    @When("the user opens the user menu")
    public void theUserOpensTheUserMenu() {
        new DashboardPage().openUserMenu();
    }

    @Then("the Logout option should be visible")
    public void theLogoutOptionShouldBeVisible() {
        assertTrue(new DashboardPage().isLogoutOptionVisible(), "Logout should appear in the user menu");
    }

    @When("the user clicks Logout")
    public void theUserClicksLogout() {
        new DashboardPage().clickLogout();
    }

    @When("the user logs out")
    public void theUserLogsOut() {
        DashboardPage dashboard = new DashboardPage();
        dashboard.openUserMenu();
        dashboard.clickLogout();
    }

    @Then("the login page should be displayed")
    public void theLoginPageShouldBeDisplayed() {
        assertTrue(new LoginPage().isDisplayed(), "Login page should be shown after logout");
    }

    @When("the user opens the dashboard URL directly")
    public void theUserOpensTheDashboardUrlDirectly() {
        new DashboardPage().openDirectly(ConfigReader.get("base.url"));
    }
}
