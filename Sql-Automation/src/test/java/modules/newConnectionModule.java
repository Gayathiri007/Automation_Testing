package modules;

import Common.BasePage;
import Common.ReusableActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import pages.newConnectionPageObjects;
import utilities.WaitUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class newConnectionModule extends BasePage {

    /**
     * Clicks the New Connection (+) icon and fails the test if the popup does not open.
     */
    public static void openNewConnectionPopup() {
        ReusableActions.click(newConnectionPageObjects.newConnectionIcon);
        WaitUtil.waitForElementToBeLocated(newConnectionPageObjects.popupTitle);
        Assert.assertTrue(driver.findElement(By.xpath(newConnectionPageObjects.popupTitle)).isDisplayed(),
                "New Connection popup is not displayed");
    }

    public static void fillConnectionDetails(String name, String description, String url, String username,
                                             String password, boolean status, boolean global) {
        ReusableActions.enterText(newConnectionPageObjects.connectionName, name);
        ReusableActions.enterText(newConnectionPageObjects.description, description);
        ReusableActions.enterText(newConnectionPageObjects.url, url);
        setToggle(newConnectionPageObjects.statusToggle, status, "Status");
        ReusableActions.enterText(newConnectionPageObjects.username, username);
        ReusableActions.enterText(newConnectionPageObjects.password, password);
        ReusableActions.click(newConnectionPageObjects.localLogin);
        setToggle(newConnectionPageObjects.globalToggle, global, "Global");
    }

    /**
     * Clicks Test Connection and returns the success toast text; fails the test if no success toast appears.
     */
    public static String testConnection() {
        ReusableActions.click(newConnectionPageObjects.testConnectionBtn);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(newConnectionPageObjects.successToast)));
        return ReusableActions.getText(newConnectionPageObjects.successToast);
    }

    public static void createConnection() {
        ReusableActions.click(newConnectionPageObjects.createConnectionBtn);
        WaitUtil.waitForElementToBeInvisible(newConnectionPageObjects.popup);
    }

    /**
     * Opens the connection dropdown and fails the test if the given connection is not listed.
     */
    public static void verifyConnectionInList(String name) {
        ReusableActions.click(newConnectionPageObjects.connectionDropdown);
        String item = newConnectionPageObjects.connectionListItem(name);
        WaitUtil.waitForElementToBeLocated(item);
        Assert.assertTrue(driver.findElement(By.xpath(item)).isDisplayed(), "Connection '" + name + "' not found in the list");
    }

    /**
     * Fails the test if any text field in the popup is not empty; returns a summary for the report.
     */
    public static String verifyFieldsBlank() {
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("Connection Name", newConnectionPageObjects.connectionName);
        fields.put("Description", newConnectionPageObjects.description);
        fields.put("URL", newConnectionPageObjects.url);
        fields.put("Oracle Cloud Username", newConnectionPageObjects.username);
        fields.put("Password", newConnectionPageObjects.password);

        StringBuilder summary = new StringBuilder();
        for (Map.Entry<String, String> field : fields.entrySet()) {
            WaitUtil.waitForElementToBeLocated(field.getValue());
            String value = driver.findElement(By.xpath(field.getValue())).getAttribute("value");
            Assert.assertTrue(value == null || value.isEmpty(), field.getKey() + " is not blank: '" + value + "'");
            summary.append(field.getKey()).append(": blank<br>");
        }
        return summary.toString();
    }

    /**
     * Clicks the button and returns the error shown inside the popup; fails the test if it does not appear.
     */
    public static String clickAndGetInlineError(String buttonPath, String expectedError) {
        ReusableActions.click(buttonPath);
        return ReusableActions.getText(newConnectionPageObjects.inlineError(expectedError));
    }

    /**
     * Clicks the button, waits for the next toast and fails the test unless it is an error containing the expected text.
     * Returns the toast text for the report.
     */
    public static String clickAndGetError(String buttonPath, String expectedError) {
        // Let any earlier toast disappear so it is not mistaken for this one
        WaitUtil.waitForElementToBeInvisible(newConnectionPageObjects.anyToast);
        ReusableActions.click(buttonPath);
        WaitUtil.waitForElementToBeLocated(newConnectionPageObjects.anyToast);
        WebElement toast = driver.findElement(By.xpath(newConnectionPageObjects.anyToast));
        String text = toast.getText().replaceAll("\\s+", " ").trim();
        Assert.assertTrue(String.valueOf(toast.getAttribute("class")).contains("p-toast-message-error"),
                "Expected an error message but got: " + text);
        Assert.assertTrue(text.contains(expectedError), "Expected error '" + expectedError + "' but got: " + text);
        return text;
    }

    /**
     * Opens the connection dropdown and fails the test if the given connection is listed.
     */
    public static void verifyConnectionNotInList(String name) {
        ReusableActions.click(newConnectionPageObjects.connectionDropdown);
        WaitUtil.waitForElementToBeLocated(newConnectionPageObjects.connectionListItems);
        Assert.assertTrue(driver.findElements(By.xpath(newConnectionPageObjects.connectionListItem(name))).isEmpty(),
                "Connection '" + name + "' should not have been created");
        ReusableActions.click(newConnectionPageObjects.connectionDropdown);
    }

    public static boolean isButtonEnabled(String path) {
        WaitUtil.waitForElementToBeLocated(path);
        WebElement button = driver.findElement(By.xpath(path));
        return button.isEnabled() && !String.valueOf(button.getAttribute("class")).contains("button-disabled");
    }

    public static void closePopup() {
        ReusableActions.click(newConnectionPageObjects.closeIcon);
        WaitUtil.waitForElementToBeInvisible(newConnectionPageObjects.popup);
    }

    /**
     * Clicks the toggle only if it is not already in the wanted state, then checks the result.
     */
    private static void setToggle(String path, boolean on, String toggleName) {
        WaitUtil.waitForElementToBeLocated(path);
        if (isToggleOn(path) != on) {
            ReusableActions.click(path);
        }
        Assert.assertEquals(isToggleOn(path), on, toggleName + " toggle is not " + (on ? "on" : "off"));
    }

    // Covers PrimeNG (p-inputswitch-checked), Angular Material (mat-checked) and aria-checked switches
    private static boolean isToggleOn(String path) {
        WebElement toggle = driver.findElement(By.xpath(path));
        String cls = String.valueOf(toggle.getAttribute("class"));
        if ("true".equals(toggle.getAttribute("aria-checked")) || cls.matches(".*(^|[\\s-])checked(\\s|$).*")) {
            return true;
        }
        List<WebElement> inputs = toggle.findElements(By.xpath(".//input"));
        return !inputs.isEmpty() && inputs.get(0).isSelected();
    }
}
