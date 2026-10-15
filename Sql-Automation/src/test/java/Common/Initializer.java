package Common;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.WaitUtil;
import java.time.Duration;


public class Initializer extends BasePage {


    public static void openBrowserAndNavigate(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("--disable-popup-blocking");
            chromeOptions.addArguments("--no-sandbox");            // critical for AWS/Linux containers
            chromeOptions.addArguments("--disable-dev-shm-usage"); // avoid /dev/shm issues

            // Read system property "headless"
            boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
            if (headless) {
                chromeOptions.addArguments("--headless=new");   // modern headless mode
                chromeOptions.addArguments("--disable-gpu");    // avoid GPU issues in Linux
                chromeOptions.addArguments("--window-size=1920,1080"); // ensure full viewport
                chromeOptions.addArguments("--start-maximized"); // mimic headed mode
                chromeOptions.addArguments("--disable-infobars"); // cleaner UI
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-browser-side-navigation");

            }

            chromeOptions.setExperimentalOption("prefs", new java.util.HashMap<String, Object>() {{
                put("download.default_directory", downloadPath);
                put("download.prompt_for_download", false);
                put("safebrowsing.enabled", true);
            }});

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(chromeOptions);

            // If not headless, maximize window
            if (!headless) {
                driver.manage().window().maximize();
            }

            driver.navigate().to("https://sql-dev.sod.troopercloud.com/login");
            WaitUtil.waitForPageLoad();
            BasePage.explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        }
    }
}
