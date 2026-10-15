package testCases;

import Common.*;
import hooks.Hooks;
import modules.commonModule;
import modules.dashboardModule;
import modules.licenseModule;
import org.testng.annotations.Test;
import pages.*;
import utilities.WaitUtil;

/**
 * Each test runs on its own browser (see Hooks): login -> steps -> logout.
 */
public class smokeTestCases extends Hooks {

    @Test(priority = 1, description = "Login and Logout")
    public void loginLogout() throws Exception {
        commonModule.login();
        commonModule.logout();
    }

    @Test(priority = 2, description = "Connecting to Database")
    public void connectDatabase() throws Exception {
        commonModule.loginAndConnect();
        commonModule.logout();
    }

    @Test(priority = 3, description = "SQL Query Data Validation")
    public void sqlQueryValidation() throws Exception {
        commonModule.loginConnectAndRunQuery();
        commonModule.logout();
    }

    @Test(priority = 4, description = "Dashboard -> Active Licensed Users")
    public void activeLicensedUsers() throws Exception {
        
        commonModule.loginAndConnect();
        dashboardModule.dashboardPage();
        WaitUtil.waitForPageLoad();
        ReportUtil.info(test, "Pop-up Data Validation", dashboardModule.activeLicensedPopup());
        ReportUtil.info(test, "Pop-up Name", ReusableActions.getText(dashboardPageObjects.popupName));
        ReportUtil.info(test, "Total no of items in the data-table", ReusableActions.getText(dashboardPageObjects.totalItems));
        ReportUtil.info(test, "Navigated to Dashboard page", ReusableActions.getCurrentUrl());
        ReportUtil.screenshot(test, "Active Licensed Users pop-up");
        ReusableActions.click(dashboardPageObjects.popupCloseBtn);
        commonModule.logout();
    }

    @Test(priority = 5, description = "Dashboard -> Used Offerings")
    public void usedOfferings() throws Exception {
        commonModule.loginAndConnect();
        dashboardModule.dashboardPage();
        WaitUtil.waitForPageLoad();
        ReportUtil.info(test, "Pop-up Data Validation", dashboardModule.usedOfferingsPopup());
        ReportUtil.info(test, "Pop-up Name", ReusableActions.getText(dashboardPageObjects.popupName));
        ReportUtil.info(test, "Total no of items in the data-table", ReusableActions.getText(dashboardPageObjects.totalItems));
        ReportUtil.screenshot(test, "Used Offerings pop-up");
        ReusableActions.click(dashboardPageObjects.popupCloseBtn);
        commonModule.logout();
    }

    @Test(priority = 6, description = "Dashboard -> Seeded Roles")
    public void seededRoles() throws Exception {
        commonModule.loginAndConnect();
        dashboardModule.dashboardPage();
        WaitUtil.waitForPageLoad();
        ReportUtil.info(test, "Pop-up Data Validation", dashboardModule.licensedSeededPopup());
        ReportUtil.info(test, "Pop-up Name", ReusableActions.getText(dashboardPageObjects.popupName));
        ReportUtil.info(test, "Total no of items in the data-table", ReusableActions.getText(dashboardPageObjects.totalItems));
        ReportUtil.screenshot(test, "Seeded Roles pop-up");
        ReusableActions.click(dashboardPageObjects.popupCloseBtn);
        commonModule.logout();
    }

    @Test(priority = 7, description = "Dashboard -> Custom Roles")
    public void customRoles() throws Exception {
        commonModule.loginAndConnect();
        dashboardModule.dashboardPage();
        WaitUtil.waitForPageLoad();
        ReportUtil.info(test, "Pop-up Data Validation", dashboardModule.customRoles());
        ReportUtil.info(test, "Pop-up Name", ReusableActions.getText(dashboardPageObjects.popupName));
        ReportUtil.info(test, "Total no of items in the data-table", ReusableActions.getText(dashboardPageObjects.totalItems));
        ReportUtil.screenshot(test, "Custom Roles pop-up");
        ReusableActions.click(dashboardPageObjects.popupCloseBtn);
        commonModule.logout();
    }

    @Test(priority = 8, description = "License Reports -> Run first report")
    public void licenseReports() throws Exception {
        commonModule.loginAndConnect();
        licenseModule.licenseReport();
        WaitUtil.waitForPageLoad();
        ReportUtil.info(test, "Navigated to License Report page", driver.getCurrentUrl());
        WaitUtil.waitForElementToBeLocated(licensePageObjects.syncWait);
        ReportUtil.info(test, "Sync Time", ReusableActions.getText(licensePageObjects.syncedTime));
        ReportUtil.screenshot(test, "License Reports page");

        licenseModule.runFirstReport();
        licenseModule.waitForReportResults();
        ReportUtil.info(test, "Report run", licensePageObjects.firstReportName);
        ReportUtil.info(test, "Column Validation", licenseModule.verifyReportColumns());
        ReportUtil.screenshot(test, "Report results");
        commonModule.logout();
    }
}
