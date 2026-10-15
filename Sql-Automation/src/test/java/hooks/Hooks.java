package hooks;

import Common.BasePage;
import Common.DataInitializer;
import Common.Initializer;
import Common.ReportUtil;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;

public class Hooks extends BasePage {

    // Once per run: test data and the report
    @BeforeSuite
    public void setUp() throws InterruptedException {

        DataInitializer.dataInitializer();
        setupReport();
    }

    // Before every @Test: a report entry (named from @Test(description)) and a fresh browser on the login page
    @BeforeMethod
    public void openBrowser(Method method) {
        String description = method.getAnnotation(Test.class).description();
        test = extent.createTest(description.isEmpty() ? method.getName() : description);
        Initializer.openBrowserAndNavigate("chrome");
    }

    // After every @Test: screenshot on failure, then close the browser
    @AfterMethod(alwaysRun = true)
    public void closeBrowser(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE && test != null) {
            test.fail(result.getThrowable());
            ReportUtil.screenshot(test, "Screenshot at failure");
        }
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        extent.flush();
        String downloadFolder = BasePage.downloadPath;

        File folder = new File(downloadFolder);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));

        if (files != null) {
            for (File file : files) {
                try {
                    Files.deleteIfExists(file.toPath());
                    System.out.println("Deleted: " + file.getName());
                } catch (Exception e) {
                    System.err.println("Failed to delete: " + file.getName() + " - " + e.getMessage());
                }
            }
        }
    }

}
