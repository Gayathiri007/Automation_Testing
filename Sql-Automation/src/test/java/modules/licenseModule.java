package modules;

import Common.BasePage;
import Common.DataInitializer;
import Common.ReusableActions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.licensePageObjects;
import utilities.WaitUtil;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

//region FutureCode
public class licenseModule extends BasePage {

    public static void connectionDB() {
        ReusableActions.click(licensePageObjects.connectionDB);
        WaitUtil.waitForPageLoad();
        BasePage.explicitWait = new WebDriverWait(driver, Duration.ofSeconds(40));
        ReusableActions.click(licensePageObjects.connectionMenu);
    }

    public static void licenseReport() {
        ReusableActions.click(licensePageObjects.menuIcon);
        WaitUtil.waitForPageLoad();
        ReusableActions.click(licensePageObjects.licenseMgmt);
        WaitUtil.waitForPageLoad();
        ReusableActions.click(licensePageObjects.licenseReport);
    }

    // Expected columns of the "Oracle User Privilege Usage History Report" results table, in order
    public static final List<String> firstReportColumns = List.of(
            "S.No", "User Name", "Privilege Name", "Privilege Code", "Role Name", "Role Assign Date",
            "User Creation Date", "User Last Login", "Privilege Last Used", "Part Number",
            "Offerings(Service Name)", "User Access Usage", "Manager Name", "Manager Email",
            "Business Unit", "Legal Employer", "Job Name", "Department Name", "Location Name");

    public static void runFirstReport() {
        ReusableActions.click(licensePageObjects.firstReportRunBtn);
    }

    /**
     * Waits for "Fetching Data..." to finish and at least one result row to appear.
     */
    public static void waitForReportResults() {
        WebDriverWait reportWait = new WebDriverWait(driver, Duration.ofSeconds(120));
        reportWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(licensePageObjects.reportFetchingData)));
        reportWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(licensePageObjects.reportResultRows), 0));
    }

    /**
     * Fails the test if the result columns don't match the expected list; returns a summary for the report.
     */
    public static String verifyReportColumns() {
        List<String> actual = driver.findElements(By.xpath(licensePageObjects.reportColumnHeaders)).stream()
                .map(th -> th.getText().trim())
                .collect(Collectors.toList());
        Assert.assertEquals(actual, firstReportColumns, "Report columns do not match");

        int rows = driver.findElements(By.xpath(licensePageObjects.reportResultRows)).size();
        return "All " + actual.size() + " columns matched: " + String.join(", ", actual) + "<br>Rows loaded: " + rows;
    }

    public static void reportGrid() throws InterruptedException {
        ReusableActions.click(licensePageObjects.existingResultBtn);
        ReusableActions.click(licensePageObjects.filterBtn);
        ReusableActions.enterText(licensePageObjects.offeringsField, DataInitializer.searchDataHandler.getFilterCriteria());
        ReusableActions.verifyElement(licensePageObjects.filteredResult, "CDM User Counts");
    }

    public static void exportReport() {
        ReusableActions.click(licensePageObjects.exportReport);
        ReusableActions.click(licensePageObjects.exportCSV);
    }

    public static void exportReportValidation() throws Exception {
        ReusableActions.refreshPage();
        ReusableActions.click(licensePageObjects.pageCountDropdown);
        ReusableActions.click(licensePageObjects.pageLimit);
        ReusableActions.click(licensePageObjects.existingResultBtn);
        ReusableActions.deletePreviousDownload();
        ReusableActions.click(licensePageObjects.exportReport);

        boolean isCSVExported = ReusableActions.exportCSV(downloadPath, licensePageObjects.exportCSV, "UI-Automation-license_summary_report_" + BasePage.dateDownload);
        // Output the result
        if (isCSVExported) {
            System.out.println("CSV file exported and validated successfully.");
        } else {
            System.out.println("CSV file not found in the specified directory.");
        }
        WaitUtil.waitForElementToBeLocated(licensePageObjects.exportToastMessage);
        WaitUtil.waitForElementToBeInvisible(licensePageObjects.exportToastMessage);
        DataInitializer.exportDataHandler.exportDataCount();
    }
//endregion
}
