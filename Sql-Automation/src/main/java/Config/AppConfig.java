package Config;

import Common.BasePage;

import java.io.File;

public class AppConfig {

    public static String workBookPath = System.getProperty("user.dir")+"/TestData.xls";
    public static String exportPath = "E:\\Rajkumar\\Projects\\SQL-Guru\\UI-Automation\\downloads\\UI-Automation-license_summary_report_"+BasePage.dateDownload+".csv";

    /**
     * Mobile config details
     */
//    public static String deviceName = "Pixel_3a_API_33_x86_64";
//    public static String platformName = "Android";
//    public static String appPackage = "com.flipkart.android";
//    public static String appActivity = "com.flipkart.android.activity.HomeFragmentHolderActivity";
}
