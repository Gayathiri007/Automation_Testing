package testCases;

import Common.DataInitializer;
import Common.ReportUtil;
import Common.ReusableActions;
import hooks.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.loginPageObjects;
import utilities.WaitUtil;

import java.time.Duration;

/**
 * Login page tests. Hooks opens a fresh browser on the login URL before each test
 * and closes it afterwards.
 */
@SuppressWarnings("null")
public class Login extends Hooks {

    @Test(priority = 1, description = "Login with valid credentials")
    public void validLogin() {
        // Step 1: Open the application URL
        String loginUrl = driver.getCurrentUrl();
        Assert.assertTrue(loginUrl.contains("/login"), "Login URL not opened. Actual URL: " + loginUrl);
        ReportUtil.info(test, "Step 1 - Application URL opened", loginUrl);

        // Step 2: Verify the login page is displayed
        WaitUtil.waitForElementToBeLocated(loginPageObjects.user_Name);
        ReusableActions.verifyElement(loginPageObjects.user_Name, "User Name field");
        ReusableActions.verifyElement(loginPageObjects.password, "Password field");
        ReusableActions.verifyElement(loginPageObjects.submit_Btn, "Submit button");
        test.pass("Step 2 - Login page is displayed");
        ReportUtil.screenshot(test, "Login page");

        // Step 3: Enter a valid username
        ReusableActions.enterText(loginPageObjects.user_Name, DataInitializer.searchDataHandler.getUsername());
        test.pass("Step 3 - Username entered");

        // Step 4: Enter a valid password
        ReusableActions.enterText(loginPageObjects.password, DataInitializer.searchDataHandler.getPassword());
        test.pass("Step 4 - Password entered");

        // Step 5: Click the Submit button
        ReusableActions.click(loginPageObjects.submit_Btn);
        test.pass("Step 5 - Submit button clicked");

        // User is authenticated and redirected away from the login page
        WebDriverWait loginWait = new WebDriverWait(driver, Duration.ofSeconds(40));
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
        } catch (TimeoutException e) {
            // The Submit click is occasionally swallowed while the page is still initialising - retry once
            ReusableActions.click(loginPageObjects.submit_Btn);
            loginWait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
        }
        loginWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginPageObjects.avatarIcon)));
        String homeUrl = ReusableActions.getCurrentUrl();
        test.pass("Expected 1 - User is authenticated successfully");
        ReportUtil.info(test, "Expected 2 - Redirected to dashboard/home page", homeUrl);
        driver.navigate().refresh();
        WaitUtil.waitForPageLoad();
        loginWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginPageObjects.avatarIcon)));
        test.pass("Expected 4 - User session is created successfully");
        ReportUtil.screenshot(test, "Dashboard after login");
    }

    @Test(priority = 2, description = "Login page - empty fields and disabled Submit button")
    public void emptyCredentialsLogin() {
        // Open the Login page
        WaitUtil.waitForElementToBeLocated(loginPageObjects.user_Name);
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "Login page not opened");
        ReportUtil.info(test, "Login page opened", driver.getCurrentUrl());

        // User Name field is blank
        WebElement userName = driver.findElement(By.xpath(loginPageObjects.user_Name));
        Assert.assertEquals(userName.getAttribute("value"), "", "User Name field is not blank");
        test.pass("User Name field is blank");

        // Password field is blank
        WebElement password = driver.findElement(By.xpath(loginPageObjects.password));
        Assert.assertEquals(password.getAttribute("value"), "", "Password field is not blank");
        test.pass("Password field is blank");

        // Submit button is disabled
        WebElement submit = driver.findElement(By.xpath(loginPageObjects.submitButton));
        Assert.assertTrue(!submit.isEnabled(), "Submit button is enabled with empty fields");
        test.pass("Submit button is disabled");
        ReportUtil.screenshot(test, "Empty login form");
    }

    @Test(priority = 3, description = "Login page - invalid username and password")
    public void invalidUsernamePasswordLogin() {

        // Open the Login page
        WaitUtil.waitForElementToBeLocated(loginPageObjects.user_Name);
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "Login page not opened");
        ReportUtil.info(test, "Login page opened", driver.getCurrentUrl());

        // Enter invalid username
        WebElement userName = driver.findElement(By.xpath(loginPageObjects.user_Name));
        userName.clear();
        userName.sendKeys("1212312312@#!@#!@#");
        test.pass("Entered invalid username");

        // Enter invalid password
        WebElement password = driver.findElement(By.xpath(loginPageObjects.password));
        password.clear();
        password.sendKeys("...;';");
        test.pass("Entered invalid password");

        // Verify Submit button is disabled
        WebElement submit = driver.findElement(By.xpath(loginPageObjects.submitButton));
        Assert.assertFalse(submit.isEnabled(),
                "Submit button is enabled for invalid username and password");
        test.pass("Submit button is disabled for invalid username and password");

        ReportUtil.screenshot(test, "Invalid credentials entered");

    }

    }    

