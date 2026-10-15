package Common;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.dashboardPageObjects;
import pages.sqlQueryPageObjects;
import utilities.ExceptionHandling;
import utilities.WaitUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

import static Common.BasePage.*;


public class ReusableActions extends dashboardPageObjects {

    public static Connection connection;

    public static Actions a;

    /**
     * This method has been created to enter text on text field.
     */
    public static void enterText(String path, String value) {
        try {
            WaitUtil.waitForElementToBeLocated(path);
            driver.findElement(By.xpath(path)).sendKeys(value);
        } catch (NoSuchElementException e) {
            ExceptionHandling.noSuchElementExcption(e);
        }
    }
    /**
     * Clears PrimeNG / Angular input and enters new text reliably.
     */
    public static void clearAndEnterText(String path, String value) throws Exception {
        try {
            WaitUtil.waitForElementToBeLocated(path);
            WebElement element = driver.findElement(By.xpath(path));

            // Focus the field
            element.click();
            Thread.sleep(200);

            // CTRL + A (select all) and DELETE
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.DELETE);
            Thread.sleep(200);

            // Enter new value
            element.sendKeys(value);

        } catch (Exception e) {
            ExceptionHandling.exception(e);
        }
    }

    /**
     * This method has been created for click action.
     */

    public static void click(String path) {
        try {
            WaitUtil.waitForElementToBeClickable(path);
            driver.findElement(By.xpath(path)).click();
        } catch (Exception e) {
            JavascriptExecutor je = (JavascriptExecutor) driver;
            je.executeScript("arguments[0].click()", driver.findElement(By.xpath(path)));
        }
    }

    public static void clickById(String path) {
        try {
            WaitUtil.waitForElementToBeClickable(path);
            driver.findElement(By.id(path)).click();
        } catch (Exception e) {
            JavascriptExecutor je = (JavascriptExecutor) driver;
            je.executeScript("arguments[0].click()", driver.findElement(By.id(path)));
        }
    }

    public static void enterRichText() {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("ace.edit('aceEditor').setValue('SELECT * FROM ap_hold_codes;');"
        );

    }

    /**
     * This method has been written to get text from object
     */

    public static String getText(String path) {
        String text = null;

        try {
            WaitUtil.waitForElementToBeLocated(path);
            text = driver.findElement(By.xpath(path)).getText();
        } catch (NoSuchElementException e) {
            ExceptionHandling.noSuchElementExcption(e);
        }
        return text;
    }

    /**
     * This method has been written to verify the element on the page.
     */
    public static void verifyElement(String path, String element) {
        try {
            if (!driver.findElement(By.xpath(path)).isDisplayed()) {
                Assert.fail("Expected: " + element + "Actual: " + element + " Not found");
            }
        } catch (NoSuchElementException e) {
            ExceptionHandling.noSuchElementExcption(e);
        }
    }

    /**
     * This method has been written to get data from database
     */

    public static String getDataBaseCellValue(String sqlQuery, String column) throws SQLException, ClassNotFoundException {
        String cellData;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(Config.sqlUrl, Config.sqlUserName, Config.sqlPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            resultSet.next();
            cellData = resultSet.getString(column);
        } catch (Exception e) {
            throw (e);
        } finally {
            connection.close();
        }
        return cellData;
    }

    public static void switchToFrameByID(String path) {
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(path)));
        driver.switchTo().frame(path);
    }

    public static void defaultContentFrame() {
        driver.switchTo().defaultContent();
    }

    public static void moveToElement(String path) {
        a = new Actions(driver);
        WaitUtil.waitForElementToBeClickable(path);
        a.moveToElement(driver.findElement(By.xpath(path)));
        a.perform();
    }

    public static void moveToElementAndClick(String path) {
        a = new Actions(driver);
        WaitUtil.waitForElementToBeClickable(path);
        a.moveToElement(driver.findElement(By.xpath(path)));
        a.click();
        a.perform();
    }

    public static void refreshPage() throws Exception {
        try {
            driver.navigate().refresh();
            WaitUtil.waitForPageLoad(); // Ensure the page has fully reloaded
        } catch (Exception e) {
            ExceptionHandling.exception(e);
        }
    }

    /**
     @return WebElement representing the clickable sort header
     */
    public static WebElement getSortableHeader(String columnXPath) {
        return explicitWait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(columnXPath))
        );
    }

        // This assumes downloadPath and dateDownload are publicly accessible or passed in
        public static void deletePreviousDownload() {
            String downloadFolder = BasePage.downloadPath;

            File folder = new File(downloadFolder);
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));

            if (files != null) {
                for (File file : files) {
                    try {
                        Files.deleteIfExists(file.toPath());
                        System.out.println("Deleted: " + file.getName());
                    } catch (Exception e) {
                        System.err.println("Failed to delete: " + file.getName() + " - " + e.getMessage());
                    }
                }
            }
        }

    public static boolean exportCSV(String downloadPath, String path, String expectedFileName) throws InterruptedException {

        // Click the export button
        WebElement exportButton = driver.findElement(By.xpath(path));
        exportButton.click();
        return Files.exists(Paths.get(downloadPath));
    }

    /**
     * Waits for page to fully load and returns stable current URL.
     */
    public static String getCurrentUrl() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Using until() style (same as ExpectedConditions.*)
            explicitWait.until(driverObject ->
                    js.executeScript("return document.readyState").equals("complete")
            );

            return driver.getCurrentUrl();

        } catch (Exception e) {
            System.out.println("Error getting stable URL: " + e.getMessage());
            return driver.getCurrentUrl(); // fallback
        }


}

    /**
     * Generic reusable method to validate popup grid data
     * for ascending and descending order.
     */
    public static String validatePopupGrid(String popupXPath,
                                           String columnXPath,
                                           String tableHeaderXPath,
                                           String cell1XPath,
                                           String cell2XPath) {
        StringBuilder popupData = new StringBuilder();

        // Open popup
        ReusableActions.click(popupXPath);
        WebElement sortHeader = ReusableActions.getSortableHeader(columnXPath);

        // --- Ascending ---
        WebElement oldRowAsc = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dashboardPageObjects.sortRow1)));
        sortHeader.click();
        explicitWait.until(ExpectedConditions.stalenessOf(oldRowAsc));

        List<WebElement> headersAsc = driver.findElements(By.xpath(tableHeaderXPath));
        String ascHeader1 = headersAsc.get(0).getText().trim();
        String ascCell1 = driver.findElement(By.xpath(cell1XPath)).getText().trim();

        popupData.append("Ascending Order Grid data:<br>")
                .append(ascHeader1).append(": ").append(ascCell1).append("<br>");

        if (cell2XPath != null && headersAsc.size() > 1) {
            String ascHeader2 = headersAsc.get(1).getText().trim();
            String ascCell2 = driver.findElement(By.xpath(cell2XPath)).getText().trim();
            popupData.append(ascHeader2).append(": ").append(ascCell2).append("<br>");
        }

        popupData.append("<br>");

        // --- Descending ---
        WebElement oldRowDesc = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dashboardPageObjects.sortRow1)));
        sortHeader.click();
        explicitWait.until(ExpectedConditions.stalenessOf(oldRowDesc));

        List<WebElement> headersDesc = driver.findElements(By.xpath(tableHeaderXPath));
        String descHeader1 = headersDesc.get(0).getText().trim();
        String descCell1 = driver.findElement(By.xpath(cell1XPath)).getText().trim();

        popupData.append("Descending Order Grid data:<br>")
                .append(descHeader1).append(": ").append(descCell1).append("<br>");

        if (cell2XPath != null && headersDesc.size() > 1) {
            String descHeader2 = headersDesc.get(1).getText().trim();
            String descCell2 = driver.findElement(By.xpath(cell2XPath)).getText().trim();
            popupData.append(descHeader2).append(": ").append(descCell2).append("<br>");
        }

        return popupData.toString();
    }

    }

