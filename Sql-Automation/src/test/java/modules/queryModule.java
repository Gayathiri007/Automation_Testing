package modules;

import Common.BasePage;
import Common.ReusableActions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.licensePageObjects;
import pages.sqlQueryPageObjects;
import utilities.WaitUtil;

import java.util.List;

public class queryModule extends BasePage {

    public static void sqlQuery() {
        ReusableActions.enterRichText();
        ReusableActions.click(sqlQueryPageObjects.runBtn);
        WaitUtil.waitForPageLoad();
        WaitUtil.waitForElementToBeLocated(sqlQueryPageObjects.queryDataToast);
    }

    public static String validateTableColumns() {
        StringBuilder output = new StringBuilder();

        try {
            // Get total count (e.g., 107)
            String countText = ReusableActions.getText(sqlQueryPageObjects.resultCount);
            int totalCount = Integer.parseInt(countText.replaceAll("[^0-9]", ""));

            output.append("Total Count: ").append(totalCount).append("<br><br>");

            // Validate Page 1
            output.append("<b>Validating Page 1</b><br>");
            validateCurrentPageRows(output);

            // If > 100, then go to Page 2
            if (totalCount > 100) {

                // Enter total count into the record field
                ReusableActions.clearAndEnterText(sqlQueryPageObjects.defaultCountField, String.valueOf(totalCount));


                // Run the query again using your existing logic
                Thread.sleep(1000);
                ReusableActions.click(sqlQueryPageObjects.runBtn);

                // Wait for Page 2 button to appear and click it
                ReusableActions.click(sqlQueryPageObjects.tablePagination);

                Thread.sleep(1000);

                output.append("<br><b>Validating Page 2</b><br>");
                validateCurrentPageRows(output);
            }

        } catch (Exception e) {
            output.append("Error: ").append(e.getMessage());
        }

        return output.toString();
    }
    private static void validateCurrentPageRows(StringBuilder output) {

        List<WebElement> headers = driver.findElements(By.xpath(sqlQueryPageObjects.tableHeader));

        WebElement cell1 = explicitWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(sqlQueryPageObjects.tableCell1)));
        WebElement cell2 = driver.findElement(By.xpath(sqlQueryPageObjects.tableCell2));

        String header1 = headers.size() > 0 ? headers.get(0).getText().trim() : "Header1";
        String header2 = headers.size() > 1 ? headers.get(1).getText().trim() : "Header2";

        output.append(header1).append(": ").append(cell1.getText().trim()).append("<br>");
        output.append(header2).append(": ").append(cell2.getText().trim()).append("<br>");
    }




}

