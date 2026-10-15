package modules;

import Common.BasePage;
import Common.DataInitializer;
import Common.ReusableActions;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.loginPageObjects;

import java.time.Duration;

public class loginModule extends BasePage {

    public static void personalUser() throws Exception {
        ReusableActions.enterText(loginPageObjects.user_Name, DataInitializer.searchDataHandler.getUsername());
        ReusableActions.enterText(loginPageObjects.password, DataInitializer.searchDataHandler.getPassword());
        BasePage.explicitWait = new WebDriverWait(driver, Duration.ofSeconds(40));
        ReusableActions.click(loginPageObjects.submit_Btn);
        // The Submit click is occasionally swallowed while the page is still initialising - retry once
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
        } catch (TimeoutException e) {
            ReusableActions.click(loginPageObjects.submit_Btn);
            // Fails here (instead of at a later step) if the credentials are rejected
            explicitWait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
        }
    }

    public static void logout() {
        ReusableActions.click(loginPageObjects.avatarIcon);
        ReusableActions.click(loginPageObjects.logoutBtn);
        explicitWait.until(ExpectedConditions.urlContains("/login"));
    }


}
