package Common;


import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ReportUtil extends BasePage{

            public static void info(ExtentTest test, String labelName, String value){

                test.info( labelName+": " +"<p style='color:#11B926'>"+value+"</p>");

            }

            /**
             * Captures the current browser window and embeds it in the report (base64, so the report stays a single file).
             */
            public static void screenshot(ExtentTest test, String title){
                try {
                    String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                    test.info(title, MediaEntityBuilder.createScreenCaptureFromBase64String(base64, title).build());
                } catch (Exception e) {
                    test.warning("Screenshot failed: " + e.getMessage());
                }
            }
}
