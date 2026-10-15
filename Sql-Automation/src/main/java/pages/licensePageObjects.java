package pages;

import Common.BasePage;

public class licensePageObjects extends BasePage {

    public static String connectionDB = "//span[contains(@class,'p-dropdown-label') and contains(text(),'Choose Connection')]";
    public static String connectionMenu = "(//li[contains(@class,'p-dropdown-item')])[1]";

    public static String menuIcon = "//img[@alt='Menu']";

    public static String licenseMgmt = "//*[@id=\"mat-expansion-panel-header-3\"]";

    public static String licenseReport = "//*[@id=\"cdk-accordion-child-3\"]//button[contains(.,'License Reports')]";

    public static String syncedTime = "//div[@class='col-6']//p[contains(.,'**Last Synced On:')]";

    public static String syncWait = "//div[@class='col-6']//p[contains(.,'AM') or contains(.,'PM')]";

    // First report card on the License Reports page, located by name
    public static String firstReportName = "Oracle User Privilege Usage History Report";

    public static String firstReportRunBtn = "//*[normalize-space()='" + firstReportName + "']/ancestor::*[.//button[contains(.,'Run')]][1]//button[contains(.,'Run')]";

    public static String reportFetchingData = "//*[normalize-space(text())='Fetching Data...']";

    public static String reportResultRows = "//table//tbody/tr";

    public static String reportColumnHeaders = "//table//thead//th";

    public static String existingResultBtn ="(//span[@class='mat-button-wrapper'])[27]";


    public static String reportToastMessage = "//div[contains(@class, 'p-toast-detail')]";

    public static String filterBtn = "(//button[@class=\'p-button-text p-button p-component p-button-icon-only\'])[1]";

    public static String offeringsField = "(//input[@type='text'])[4]";

    public static String filteredResult = "(//div[normalize-space()='CDM User Counts'])[1]";

    public static String exportReport = "(//span[@class='p-button-icon p-button-icon-left pi pi-download'])[1]";

    public static String exportCSV = "(//a[@role='menuitem'])[1]";

    public static String exportToastMessage = "//div[contains(@class, 'p-toast-detail') and contains(text(), 'exported')]";


    public static String pageCountDropdown = "(//span[@class='p-dropdown-trigger-icon ng-tns-c45-6 pi pi-chevron-down'])[1]";
    public static String pageLimit = "(//span[@class='ng-star-inserted'][normalize-space()='200'])[1]";

    public static String totalEntriesCount = "(//span[@class='p-paginator-current ng-star-inserted'])[1]";


}
