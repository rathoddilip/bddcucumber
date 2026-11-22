package com.orangehrm.bdd.pages;

import com.orangehrm.bdd.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/** OrangeHRM login page: /web/index.php/auth/login */
public class LoginPage extends BasePage {

    private static final By HEADING = By.xpath("//h5[normalize-space()='Login']");
    private static final By USERNAME = By.name("username");
    private static final By PASSWORD = By.name("password");
    private static final By LOGIN_BUTTON = By.cssSelector("button[type='submit']");
    private static final By ALERT_MESSAGE = By.cssSelector(".oxd-alert-content-text");
    private static final By FIELD_ERRORS = By.cssSelector(".oxd-input-field-error-message");
    private static final By USERNAME_ERROR = By.xpath(
            "//input[@name='username']/ancestor::div[contains(@class,'oxd-input-group')]"
                    + "//span[contains(@class,'oxd-input-field-error-message')]");
    private static final By PASSWORD_ERROR = By.xpath(
            "//input[@name='password']/ancestor::div[contains(@class,'oxd-input-group')]"
                    + "//span[contains(@class,'oxd-input-field-error-message')]");

    public void open() {
        driver.get(ConfigReader.get("base.url"));
        visible(USERNAME);
    }

    public void enterUsername(String username) {
        type(USERNAME, username);
    }

    public void enterPassword(String password) {
        type(PASSWORD, password);
    }

    public void clickLogin() {
        click(LOGIN_BUTTON);
    }

    public void loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isDisplayed() {
        waitForUrlEnding("/auth/login");
        return isVisible(HEADING) && isVisible(USERNAME) && isVisible(PASSWORD);
    }

    public String alertMessage() {
        return visible(ALERT_MESSAGE).getText().trim();
    }

    public String usernameError() {
        return visible(USERNAME_ERROR).getText().trim();
    }

    public String passwordError() {
        return visible(PASSWORD_ERROR).getText().trim();
    }

    public int fieldErrorCount() {
        List<WebElement> errors = allVisible(FIELD_ERRORS);
        return errors.size();
    }
}
