package testCases;

import Common.*;
import hooks.Hooks;
import modules.commonModule;
import modules.newConnectionModule;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.newConnectionPageObjects;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * New Connection tests. Each test runs on its own browser (see Hooks): login -> steps -> logout.
 */
public class NewConnection extends Hooks {

    @Test(priority = 1, description = "Create a new connection with valid details")
    public void createNewConnection() throws Exception {
        // Timestamp suffix keeps the name unique across re-runs
        String connectionName = "SAR1_" + new SimpleDateFormat("MMdd_HHmmss").format(new Date());

        commonModule.login();

        newConnectionModule.openNewConnectionPopup();
        ReportUtil.screenshot(test, "New Connection popup");

        newConnectionModule.fillConnectionDetails(
                connectionName,
                "Automation testing",
                "https://iatgqy-dev2.fa.ocs.oraclecloud.com/",
                "sarvar.hussain@mrcglobal.com",
                "welcome0",
                true,
                false);
        ReportUtil.info(test, "Connection Name", connectionName);
        ReportUtil.screenshot(test, "Connection details entered");

        ReportUtil.info(test, "Test Connection", newConnectionModule.testConnection());
        ReportUtil.screenshot(test, "Test Connection success");

        newConnectionModule.createConnection();
        newConnectionModule.verifyConnectionInList(connectionName);
        test.pass("Connection '" + connectionName + "' is listed");
        ReportUtil.screenshot(test, "New connection in the list");

        commonModule.logout();
    }

    @Test(priority = 2, description = "New Connection popup - blank fields and disabled buttons by default")
    public void newConnectionDefaults() throws Exception {
        commonModule.login();

        
        newConnectionModule.openNewConnectionPopup();
        ReportUtil.info(test, "Fields Validation", newConnectionModule.verifyFieldsBlank());
        ReportUtil.screenshot(test, "New Connection popup - default state");

        boolean createEnabled = newConnectionModule.isButtonEnabled(newConnectionPageObjects.createConnectionBtn);
        ReportUtil.info(test, "Create Connection button", createEnabled ? "Enabled" : "Disabled");
        Assert.assertFalse(createEnabled, "Create Connection button should be disabled");

        boolean testEnabled = newConnectionModule.isButtonEnabled(newConnectionPageObjects.testConnectionBtn);
        ReportUtil.info(test, "Test Connection button", testEnabled ? "Enabled" : "Disabled");
        Assert.assertFalse(testEnabled, "Test Connection button should be disabled");

        newConnectionModule.closePopup();
        commonModule.logout();
    }

    @Test(priority = 3, description = "New Connection with invalid details - Create and Test Connection show errors")
    public void newConnectionInvalidDetails() throws Exception {
        String connectionName = "SAR1_INV_" + new SimpleDateFormat("MMdd_HHmmss").format(new Date());

        commonModule.login();

        newConnectionModule.openNewConnectionPopup();
        newConnectionModule.fillConnectionDetails(
                connectionName,
                "",
                "https://invalid-url.oraclecloud.com/",
                "invalid.user@invalid.com",
                "invalid123",
                true,
                false);
        ReportUtil.info(test, "Connection Name", connectionName);
        ReportUtil.screenshot(test, "Invalid details entered");

        boolean createEnabled = newConnectionModule.isButtonEnabled(newConnectionPageObjects.createConnectionBtn);
        boolean testEnabled = newConnectionModule.isButtonEnabled(newConnectionPageObjects.testConnectionBtn);
        ReportUtil.info(test, "Create Connection button", createEnabled ? "Enabled" : "Disabled");
        ReportUtil.info(test, "Test Connection button", testEnabled ? "Enabled" : "Disabled");
        Assert.assertTrue(createEnabled, "Create Connection button should be enabled");
        Assert.assertTrue(testEnabled, "Test Connection button should be enabled");

        ReportUtil.info(test, "Create Connection error",
                newConnectionModule.clickAndGetInlineError(newConnectionPageObjects.createConnectionBtn, "Check your credentials"));
        ReportUtil.screenshot(test, "Create Connection error");

        ReportUtil.info(test, "Test Connection error",
                newConnectionModule.clickAndGetError(newConnectionPageObjects.testConnectionBtn, "Invalid connection details!"));
        ReportUtil.screenshot(test, "Test Connection error");

        newConnectionModule.closePopup();
        newConnectionModule.verifyConnectionNotInList(connectionName);
        test.pass("Connection '" + connectionName + "' was not created");

        commonModule.logout();
    }
}
