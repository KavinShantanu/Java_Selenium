package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.IframeHandling;
import utils.Report;

public class IframeTest extends BaseTest {


    @Test(description = "Verify successful Iframe test")
    public void verifyIframeHandling() throws InterruptedException {
        IframeHandling obj = new IframeHandling(driver);

        Report.info("website login and switch to frame");
        obj.login();

        Report.info("Verify the user switches to single frame");
        obj.switchToIframe();

        Report.info("Verify the text box inside frame");
        obj.enterText();

        Report.info("handling Iframe and Enter the text box");
        obj.multiIFrame();


    }
}


