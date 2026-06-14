package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.CommonEvents;
import utils.Report;

import java.time.Duration;

public class LoginPage {
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By dashboardIcon = By.xpath("//h6[text()='Dashboard']");

    private WebDriver driver;
    private final CommonEvents commonEvents;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.commonEvents = new CommonEvents(driver);
    }

    public void loginToHRM(String user, String pass, boolean b) {
        commonEvents.sendText(usernameField, "username", user, true);
        commonEvents.sendText(passwordField, "Password", pass, true);
        commonEvents.clickElement(loginButton, "Login button");

    }

    public void dashboardCheck()
    {

        commonEvents.fluentWaitForElement(dashboardIcon,500);
        boolean homePage = driver.getCurrentUrl().contains("dashboard");
        if (homePage) {
            Report.info("Dashboard validation milestone check: SUCCESS");
        } else {
            Report.error("Dashboard validation milestone check: FAILED", null);
        }
    }

}
