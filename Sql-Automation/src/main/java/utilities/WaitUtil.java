package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static Common.BasePage.driver;
import static Common.BasePage.explicitWait;


public class WaitUtil {

    static long pageLoadTime = 90;

    public static void waitForPageLoad(){
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTime));
    }
    public static void waitForElementToBeLocated(String path){

        waitForPageLoad();
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
    }

    public static void waitForElementToBeClickable(String path){
        waitForPageLoad();
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath(path)));
    }

    public static void waitForElementToBeInvisible(String path) {
        waitForPageLoad();
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(path)));
    }
}
