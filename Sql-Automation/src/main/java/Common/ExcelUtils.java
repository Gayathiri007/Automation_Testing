package Common;



import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    public static void readExcelGetDataFromRow() throws IOException {

        try (FileInputStream file = new FileInputStream("C:\\Users\\Lenovo\\IdeaProjects\\eCom\\TestData.xls");
             XSSFWorkbook wb = new XSSFWorkbook(file)) {
            XSSFSheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(0);
            System.out.println(row.getCell(0).getStringCellValue());
            System.out.println(row.getCell(1).getStringCellValue());
        }
    }

}
