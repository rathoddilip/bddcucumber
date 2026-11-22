package com.orangehrm.bdd.pages;

import org.openqa.selenium.By;

/** OrangeHRM dashboard (landing page after login) plus the top-right user menu. */
public class DashboardPage extends BasePage {

    public static final String PATH = "/dashboard/index";

    private static final By HEADER_TITLE = By.cssSelector(".oxd-topbar-header-breadcrumb h6");
    private static final By SIDE_MENU = By.cssSelector("aside.oxd-sidepanel");
    private static final By USER_MENU = By.cssSelector(".oxd-userdropdown-tab");
    private static final By LOGOUT_LINK = By.xpath("//a[@role='menuitem' and normalize-space()='Logout']");

    public boolean isDisplayed() {
        waitForUrlEnding(PATH);
        return isVisible(SIDE_MENU) && "Dashboard".equals(visible(HEADER_TITLE).getText().trim());
    }

    public void openUserMenu() {
        click(USER_MENU);
    }

    public boolean isLogoutOptionVisible() {
        return isVisible(LOGOUT_LINK);
    }

    public void clickLogout() {
        click(LOGOUT_LINK);
    }

    /** Opens the dashboard URL directly (used to check the session really ended). */
    public void openDirectly(String baseUrl) {
        driver.get(baseUrl.replace("/auth/login", PATH));
    }
}
