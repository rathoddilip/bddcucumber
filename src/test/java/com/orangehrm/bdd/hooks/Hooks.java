package com.orangehrm.bdd.hooks;

import com.orangehrm.bdd.driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/** Fresh browser for every scenario; screenshot attached to the report when a scenario fails. */
public class Hooks {

    @Before
    public void startBrowser() {
        DriverManager.start();
    }

    @After
    public void stopBrowser(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                byte[] png = ((TakesScreenshot) DriverManager.get()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(png, "image/png", "Failure screenshot");
            }
        } finally {
            DriverManager.quit();
        }
    }
}
