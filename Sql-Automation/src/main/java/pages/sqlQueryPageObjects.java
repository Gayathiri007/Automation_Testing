package pages;

import Common.BasePage;

public class sqlQueryPageObjects extends BasePage {

    public static String sqlQueryMenu = "(//div[normalize-space()='SQL Query'])[1]";
    public static String queryEditor = "(//textarea[@class='ace_text-input'])[1]";

    public static String defaultCountField = "//input[@class='p-component p-filled p-inputtext p-inputnumber-input']";

    public static String runBtn = "(//div[@class='btns'])[2]";

    public static String queryDataToast = "//div[contains(@class, 'p-toast-detail') and contains(text(), 'fetched')]";

    public static String resultCount = "(//div[@class='count-right-align'])[1]";

    public static String tableHeader = "//*[@id='editorTable']//table/thead/tr/th";

    public static String tableCell1 = "//*[@id='editorTable']/div[2]/p-table/div/div/div/div[2]/table/tbody/tr[1]/td[1]";

    public static String tableCell2 = "//*[@id='editorTable']/div[2]/p-table/div/div/div/div[2]/table/tbody/tr[1]/td[2]";

    public static String tablePagination = "//button[normalize-space()='2']";


}