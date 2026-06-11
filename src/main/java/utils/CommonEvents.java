package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.beans.Visibility;
import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.Properties;


public class CommonEvents {
    private final WebDriver driver;
    private final WebDriverWait wait;


    //constructor
    public CommonEvents(WebDriver browserDriver) {
        this.driver = browserDriver;

        Properties prop = ConfigReader.fetchProperties();
        int timeoutInSeconds = Integer.parseInt(prop.getProperty("timeout"));

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));

    }

    private void attachScreenshotToAllure(String screenshotName) {
        try {
            byte[] image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, new ByteArrayInputStream(image));
            LogRecorder.debug("Screenshot attached: " + screenshotName);
        } catch (Exception e) {
            LogRecorder.error("Failed to capture and attach screenshot", e);
        }
    }

    public WebElement getElement(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            LogRecorder.error("Timeout reached! Element not visible: " + locator.toString(), e);
            attachScreenshotToAllure("Failure - Element Not Visible");
            throw e;
        }
    }


    public void clickElement(By locator, String elementName, boolean screenshot) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            getElement(locator).click();
            LogRecorder.info("Successfully clicked on: " + elementName);

            // Evaluates your true/false requirement condition cleanly
            if (screenshot) {
                attachScreenshotToAllure("Snapshot after clicking " + elementName);
            }
        } catch (Exception e) {
            LogRecorder.error("Failed to click on element: " + elementName, e);
            throw e;
        }
    }

    public void sendText(By locator, String elementName, String text, boolean screenshot) {
        try {
            WebElement content = getElement(locator);
            content.sendKeys(text);
            LogRecorder.info("text successfully typed into field:" + elementName);
        } catch (Exception e) {
            LogRecorder.error("Failed to add the text into the field:" + elementName, e);
            throw e;
        }

    }

}

