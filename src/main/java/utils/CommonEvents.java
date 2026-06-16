package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.JavascriptExecutor;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.Properties;


public class CommonEvents {
    private final WebDriver driver;
    private final WebDriverWait driverWait;
    private final int timeoutInSeconds;

    //constructor
    public CommonEvents(WebDriver browserDriver) {
        this.driver = browserDriver;

        Properties prop = ConfigReader.fetchProperties();
        timeoutInSeconds = Integer.parseInt(prop.getProperty("timeout"));

        this.driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));

    }

    private void attachScreenshotToAllure(String screenshotName) {
        try {
            byte[] image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, new ByteArrayInputStream(image));
            Report.debug("Screenshot attached: " + screenshotName);
        } catch (Exception e) {
            Report.error("Failed to capture and attach screenshot", e);
        }
    }

    public WebElement getElement(By locator) {
        try {
            return driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            Report.error("Timeout reached! Element not visible: " + locator.toString(), e);
            attachScreenshotToAllure("Failure - Element Not Visible");
            throw e;
        }
    }


    public void clickElement(By locator, String elementName) {
        try {
            driverWait.until(ExpectedConditions.elementToBeClickable(locator));
            getElement(locator).click();
            Report.info("Successfully clicked on: " + elementName);

        } catch (Exception e) {
            Report.error("Failed to click on element: " + elementName, e);
            throw e;
        }
    }

    public void jsClick(By locator, String elementName) {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].click()", getElement(locator));

        } catch (Exception e) {
            Report.error("Failed to click on element: " + elementName, e);
            throw e;
        }
    }

    public void iFrameWaitAndSwitch(By locator)
    {
        try {
            driverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
        } catch (Exception e) {
            Report.error("Iframe is failed to load: " + locator, e);
            throw e;
        }
    }

    public void selectByVisibleText(By locator, String elementName, String text) {
        try {
            Select dropdown = new Select(getElement(locator));
            dropdown.selectByVisibleText(text);
        } catch (Exception e) {
            Report.error("Element not found: " + elementName, e);
            throw e;
        }
    }

    public void selectByIndex(By locator, String elementName, int index) {
        try {
            Select dropdown = new Select(getElement(locator));
            dropdown.selectByIndex(index);
        } catch (Exception e) {
            Report.error("Element not found: " + elementName, e);
            throw e;
        }
    }

    // method to hover on the element
    public void scrollToLocator(By locator, String elementName, boolean screenshot) {
        try {
            Actions act = new Actions(driver);
            act.scrollToElement(getElement(locator)).perform();

            if (screenshot) {
                attachScreenshotToAllure("Snapshot after scrolling to the element " + elementName);
            }

        } catch (Exception e) {
            Report.error("Unable to scroll to the Element " + elementName, e);
            throw e;
        }
    }

    public void hoverAndClick(By clickLocator, String elementName, boolean screenshot) {
        try {
            Actions act = new Actions(driver);
            act.moveToElement(getElement(clickLocator))
                    .click()
                    .build()
                    .perform();

            if (screenshot) {
                attachScreenshotToAllure("Snapshot after hovering to the element " + elementName);
            }

        } catch (Exception e) {
            Report.error("Unable to find to the Element " + elementName, e);
            throw e;
        }
    }

    public void doubleClick(By locator, String elementName, boolean screenshot) {
        try {
            Actions act = new Actions(driver);
            act.doubleClick(getElement(locator)).perform();
            if (screenshot) {
                attachScreenshotToAllure("Snapshot after double clicking the element " + elementName);
            }

        } catch (Exception e) {
            Report.error("Unable to find to the Element " + elementName, e);
            throw e;
        }
    }

    public void dragDrop(By locator, By srcElement, By desElement, String src, String des, boolean screenshot) {
        try {
            Actions act = new Actions(driver);
            act.dragAndDrop(getElement(srcElement), getElement(desElement));
            if (screenshot) {
                attachScreenshotToAllure("Snapshot after scrolling to the element " + des);
            }

        } catch (Exception e) {
            Report.error("Unable to find to the Element " + des, e);
            throw e;
        }
    }


    public void sendText(By locator, String elementName, String text, boolean screenshot) {
        try {
            WebElement content = getElement(locator);
            content.sendKeys(text);
            Report.info("text successfully typed into field:" + elementName);
            if (screenshot) {
                attachScreenshotToAllure("Snapshot after entering the text " + elementName);
            }
        } catch (Exception e) {
            Report.error("Failed to add the text into the field:" + elementName, e);
            throw e;
        }

    }

    public WebElement fluentWaitForElement(By locator, int polling) {
        Wait<WebDriver> fluent = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(polling))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        try {
            return fluent.until(_ -> {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed() && element.isEnabled()) {
                    return element;
                }
                return null;
            });

        } catch (Exception e) {
            Report.error("Fluent Wait timeout broken! Element failed to find"
                    + timeoutInSeconds + " seconds: " + locator.toString(), e);
            throw e;
        }


    }
}