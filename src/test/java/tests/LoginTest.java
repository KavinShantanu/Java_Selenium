package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.Report;

public class LoginTest extends BaseTest {

    @Test(description = "Verify successful user login to OrangeHRM Portal.")
    public void verifyLoginPage() {
        LoginPage obj = new LoginPage(driver);

        Report.info("Verify the user login to OrangeHRM Portal");

        obj.loginToHRM("Admin", "admin123", true);


        Report.info("Verify the Dashboard login page");
        obj.dashboardCheck();

    }

}

