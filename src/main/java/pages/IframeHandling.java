package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.CommonEvents;

public class IframeHandling {
    private By frame = By.id("singleframe");
    private By inputfield = By.xpath("//input[@type='text']");
    private By login = By.id("enterimg");
    private By switchTo = By.xpath("(//a[@class='dropdown-toggle'])[1]");
    private By frameButton = By.xpath("//a[contains(text(),'Frames')]");
    private By multiFrameButton = By.xpath("//a[contains(text(),'Iframe with in an Iframe')]");
    private By iFrameFirst= By.xpath("//div[@id='Multiple']/iframe");
    private By iFrameSecond = By.xpath("//div[@class='iframe-container' and ./h5[contains(text(),'Nested iFrames')]]/iframe");

    private WebDriver driver;
    private CommonEvents commonEvents;


    public IframeHandling(WebDriver driver) {
        this.driver = driver;
        this.commonEvents = new CommonEvents(driver);
    }

    public void switchToIframe() {
        commonEvents.fluentWaitForElement(frame, 500);
        driver.switchTo().frame(commonEvents.getElement(frame));
    }

    public void enterText() throws InterruptedException {

        commonEvents.sendText(inputfield, "inputfield", "hello World", true);
        driver.switchTo().defaultContent();

    }

    public void login() {

        commonEvents.clickElement(login, "login");
        commonEvents.jsClick(switchTo, "switchTo");
        //commonEvents.fluentWaitForElement(switchTo,500);

        commonEvents.hoverAndClick(frameButton, "switchTo", true);

    }

    public void multiIFrame()
    {
        // Open the nested frames section
        commonEvents.jsClick(multiFrameButton, "multiFrameButton");

        // Wait for the parent iframe to be present and switch into it
        commonEvents.iFrameWaitAndSwitch(iFrameFirst);

        // Wait for the child iframe to be present and switch into it
        commonEvents.iFrameWaitAndSwitch(iFrameSecond);

        // Interact with element inside nested iframe
        commonEvents.sendText(inputfield, "inputfield", "hello World", true);

        // Return to main document
        driver.switchTo().defaultContent();

    }




}
