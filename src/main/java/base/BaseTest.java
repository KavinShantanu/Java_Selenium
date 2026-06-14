package base;

import org.openqa.selenium.chrome.ChromeOptions;
import utils.ConfigReader;
import utils.Report;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Report;

import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    //variable declaration
    protected WebDriver driver;
    public Properties prop;
    protected ChromeOptions options = new ChromeOptions();

    @BeforeMethod
    public void setUp()
    {

        prop = ConfigReader.fetchProperties();

        String browserName = prop.getProperty("browser").toLowerCase();
        String url = prop.getProperty("url").toLowerCase();
        int timeout = Integer.parseInt(prop.getProperty("timeout"));

        switch (browserName) {
            case "chrome" -> {
                driver = new ChromeDriver(options);
                options.addArguments("--incognito");
            }
            case "edge" -> driver = new EdgeDriver();
            case "firefox" -> driver = new FirefoxDriver();
            default -> {
                Report.error("Fatal: Unsupported browser configuration submitted: " + browserName, null);
                throw new RuntimeException("Browser type not supported" + browserName);
            }
        }


        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));

        Report.info("Navigating active browser session to application endpoint: " + url);
        driver.get(url);

    }

    @AfterMethod
    public void tearDown()
    {
        if (driver != null) {
            Report.info("Tearing down browser session and releasing system resources safely.");
            driver.quit();
        }
    }

}
