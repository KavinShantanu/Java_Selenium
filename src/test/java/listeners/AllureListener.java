package listeners;

import io.qameta.allure.testng.AllureTestNg;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Thin wrapper listener that delegates to AllureTestNg.
 * Placing this in src/test/java/listeners allows centralized listener configuration.
 */
public class AllureListener implements ITestListener {
    private final AllureTestNg delegate = new AllureTestNg();

    @Override
    public void onStart(ITestContext context) {
        delegate.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        delegate.onFinish(context);
    }

    @Override
    public void onTestStart(ITestResult result) {
        delegate.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        delegate.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        delegate.onTestFailure(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        delegate.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        delegate.onTestFailedButWithinSuccessPercentage(result);
    }
}
