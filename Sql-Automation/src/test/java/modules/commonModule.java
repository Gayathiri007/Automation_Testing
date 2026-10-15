package modules;

import Common.BasePage;
import Common.ReportUtil;
import Common.ReusableActions;
import pages.loginPageObjects;
import pages.sqlQueryPageObjects;
import utilities.WaitUtil;

/**
 * Common steps shared across test cases. Each step logs into the current test's report entry
 * (created per @Test in Hooks).
 */
public class commonModule extends BasePage {

    public static void login() throws Exception {
        loginModule.personalUser();
        test.pass("Personal user logged-in");
        ReportUtil.screenshot(test, "After login");
    }

    public static void connectToDatabase() {
        licenseModule.connectionDB();
        WaitUtil.waitForPageLoad();
        ReportUtil.info(test, "Connection Status", ReusableActions.getText(loginPageObjects.toastMessage));
        ReportUtil.screenshot(test, "Connection selected");
    }

    public static void runSqlQuery() {
        queryModule.sqlQuery();
        ReportUtil.info(test, "Report Status", ReusableActions.getText(sqlQueryPageObjects.queryDataToast));
        ReportUtil.info(test, "Total no of items in the SQL Result Table", ReusableActions.getText(sqlQueryPageObjects.resultCount));
        ReportUtil.info(test, "Table Data Validation for first two columns", queryModule.validateTableColumns());
        ReportUtil.screenshot(test, "SQL query result");
    }

    public static void logout() {
        loginModule.logout();
        test.pass("Logged out");
    }

    /**
     * Login -> connect to database.
     */
    public static void loginAndConnect() throws Exception {
        login();
        connectToDatabase();
    }

    /**
     * Login -> connect to database -> run SQL query.
     */
    public static void loginConnectAndRunQuery() throws Exception {
        loginAndConnect();
        runSqlQuery();
    }
}
