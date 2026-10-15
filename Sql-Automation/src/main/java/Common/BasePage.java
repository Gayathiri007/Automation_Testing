package Common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.JsonFormatter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.WaitUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;


public class BasePage {

    public static WebDriver driver;
    private static final String json_archive ="target/json/jsonArchive.json";
    public static ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    public static ExtentTest test;
    public static JsonFormatter jsonFormatter;

    public static Date date = new Date();
    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    public static String dateForm = formatter.format(date);

    static ZoneId zoneId = ZoneId.of("America/New_York");

    public static String dateDownload = ZonedDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    public static String reportPath = "reports/report_"+dateForm+".html";
    public static WebDriverWait explicitWait;

    public static String screenShotPath = "src/main/resources/screenshots_"+dateForm+".png";

//  public static String downloadPath = "E:\\Rajkumar\\Projects\\SQL-Guru\\UI-Automation\\src\\main\\resources";

    public static String downloadPath = "E:\\Rajkumar\\Projects\\SQL-Guru\\UI-Automation\\downloads";

    public static ExtentReports extentReports = new ExtentReports();

    public void setupReport(){
        sparkReporter = new ExtentSparkReporter(reportPath);
        extent =new ExtentReports();
        extent.attachReporter(sparkReporter);
        jsonFormatter = new JsonFormatter(json_archive);
}
}
