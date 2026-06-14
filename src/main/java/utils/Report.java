package utils;


import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Report {
    //initialize the SLF4j logger
    private static final Logger log = LoggerFactory.getLogger(Report.class);

    public static void info(String message) {
        log.info(message);
        Allure.step(message);
    }

    public static void error(String message, Throwable throwable) {
        log.error(message, throwable);
        Allure.step("🔴 ERROR: " + message);
        if (throwable != null) {
            Allure.step("Exception Details: " + throwable.getMessage());
        }
    }

    public static void debug(String message) {
        log.debug(message);
    }
}
