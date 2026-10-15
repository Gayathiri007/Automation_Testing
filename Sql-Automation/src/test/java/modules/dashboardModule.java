package modules;

import Common.BasePage;
import Common.ReusableActions;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.dashboardPageObjects;
import pages.licensePageObjects;
import pages.sqlQueryPageObjects;
import utilities.WaitUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static Common.ReusableActions.validatePopupGrid;

public class dashboardModule extends BasePage {

    public static String screenshotPath;

    public static void dashboardPage() throws IOException, InterruptedException {
        ReusableActions.click(licensePageObjects.menuIcon);
        WaitUtil.waitForPageLoad();
        ReusableActions.click(licensePageObjects.licenseMgmt);
        WaitUtil.waitForPageLoad();
        ReusableActions.click(dashboardPageObjects.dashboardOption);
        WaitUtil.waitForPageLoad();
    }

    public static String activeLicensedPopup() {
        return validatePopupGrid(
                dashboardPageObjects.utilizingLicenses,
                dashboardPageObjects.columnXPath,
                dashboardPageObjects.tableHeader,
                dashboardPageObjects.tableCell1,
                dashboardPageObjects.tableCell2
        );
    }

    public static String usedOfferingsPopup() {
        return validatePopupGrid(
                dashboardPageObjects.usedOfferings,
                dashboardPageObjects.columnXPath,
                dashboardPageObjects.tableHeader,
                dashboardPageObjects.tableCell1,
                null
        );
    }

    public static String licensedSeededPopup() {
        return validatePopupGrid(
                dashboardPageObjects.seededRoles,
                dashboardPageObjects.columnXPath,
                dashboardPageObjects.tableHeader,
                dashboardPageObjects.tableCell1,
                dashboardPageObjects.tableCell2
        );
    }

    public static String customRoles() {
        return validatePopupGrid(
                dashboardPageObjects.customRoles,
                dashboardPageObjects.columnXPath,
                dashboardPageObjects.tableHeader,
                dashboardPageObjects.tableCell1,
                dashboardPageObjects.tableCell2
        );
    }

    }


