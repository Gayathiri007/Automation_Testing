package pages;

import Common.BasePage;

public class dashboardPageObjects extends BasePage {

    public static String dashboardOption = "//*[@id=\"cdk-accordion-child-3\"]/div/div/button[1]";

    public static String utilizingLicenses = "(//div[@class='row'])[2]";

    public static String popupName = "(//span[@class='ui-lib-toolbar-header'])[1]";

    // Call the reusable method
    public static String columnXPath = "(//th[@role='columnheader'])[1]";

//    public static String cellXPath = "(//tbody/tr/td[1])";

    public static String totalItems = "(//div[@class='ui-paginator-current'])[1]";

    public static String popupCloseBtn = "//mat-icon[contains(@class,'close-icon')]";

    public static String tableHeader = "//table[contains(@class,'p-datatable-table')]//thead/tr/th";

    public static String tableCell1 = "(//td[@class='ng-star-inserted'])[1]";

    public static String tableCell2 = "(//td[@class='ng-star-inserted'])[2]";

    public static String sortRow1 = "//table/tbody/tr[1]";

    public static String usedOfferings = "(//div[@class='row'])[3]";

    public static String seededRoles = "(//div[@class='row'])[5]";

    public static String customRoles = "(//div[@class='row'])[6]";

}
