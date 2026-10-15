package pages;

import Common.BasePage;

/**
 * Locators for the New Connection (+) popup (a mat-dialog with an Angular reactive form).
 */
public class newConnectionPageObjects extends BasePage {

    // SQL Query toolbar (+) icon - not the purple (+) next to the "Query 1" tab, which opens a new query tab
    public static String newConnectionIcon = "//div[contains(@class,'act-btns')][img[@alt='New Connection']]";

    public static String popup = "//mat-dialog-container";

    public static String popupTitle = popup + "//span[normalize-space()='New Connection']";

    public static String closeIcon = popup + "//img[@alt='Close']";

    public static String connectionName = popup + "//input[@formcontrolname='connectionName']";

    public static String description = popup + "//input[@formcontrolname='description']";

    public static String url = popup + "//input[@formcontrolname='url']";

    // p-inputswitch root div - carries the 'p-inputswitch-checked' class when on
    public static String statusToggle = popup + "//p-inputswitch[@formcontrolname='status']/div";

    public static String username = popup + "//input[@formcontrolname='oracleCloudUsername']";

    public static String password = popup + "//input[@formcontrolname='password']";

    public static String localLogin = popup + "//mat-radio-button[@value='local']//label";

    public static String globalToggle = popup + "//p-inputswitch[@formcontrolname='isGlobal']/div";

    public static String testConnectionBtn = popup + "//button[.//span[normalize-space()='Test Connection']]";

    public static String createConnectionBtn = popup + "//button[.//span[normalize-space()='Create Connection']]";

    public static String successToast = "//div[contains(@class,'p-toast-message-success')]//div[contains(@class,'p-toast-detail')]";

    // Red validation text shown inside the popup (e.g. "Check your credentials!" after Create Connection)
    public static String inlineError(String text) {
        return popup + "//*[not(*)][contains(normalize-space(),\"" + text + "\")]";
    }

    // Any toast (success, error, warn, info) - the class tells the type
    public static String anyToast = "(//div[contains(@class,'p-toast-message ') or contains(@class,'p-toast-message')][contains(@class,'p-toast-message-')])[1]";

    public static String connectionListItems = "//li[contains(@class,'p-dropdown-item')]";

    // Connection dropdown in the header, and the entry for a given connection name inside it
    public static String connectionDropdown = "(//span[contains(@class,'p-dropdown-label')])[1]";

    public static String connectionListItem(String name) {
        return "//li[contains(@class,'p-dropdown-item')][normalize-space()='" + name + "']";
    }
}
