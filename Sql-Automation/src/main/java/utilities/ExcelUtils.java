package utilities;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExcelUtils {

    public static ArrayList<Map<String, String>> getMapFromExcel(String workBookPath, String sheetName, String testName){

        ArrayList<String> colNames =new ArrayList<String>();
        ArrayList<Map<String, String>> mapArray = null;

        HSSFRow row = null;
        HSSFSheet sheet = null;

        int sheetRowCount = 0;
        int sheetColCount = 0;

        Map<String, String> excelRowMap = null;

        try{
            FileInputStream file = new FileInputStream(new File(workBookPath));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            sheet =    workbook.getSheet(sheetName);
            sheetRowCount = sheet.getPhysicalNumberOfRows();
            mapArray = new ArrayList<Map<String,String>>(sheetRowCount-1);

            row =sheet.getRow(0);
            for(int i=0; i<row.getPhysicalNumberOfCells(); i++)
            {
                colNames.add(""+row.getCell(i).getStringCellValue());
            }
            sheetColCount = colNames.size();

            for(int i=1; i<sheetRowCount; i++)
            {
                row =sheet.getRow(i);

                excelRowMap =new LinkedHashMap<String,String>(sheetColCount);

                for(int c=0; c<sheetColCount; c++){
                    String key = colNames.get(c);
                    String value = null;

                    if(row.getCell(c).getCellType().equals(CellType.STRING)){
                        value =row.getCell(c).getStringCellValue();
                    }
                    if(row.getCell(c).getCellType().equals(CellType.NUMERIC)){
                        double numericValue = row.getCell(c).getNumericCellValue();
                        value = String.valueOf(numericValue);
                    }
                    excelRowMap.put(key,value);
                }
                mapArray.add(excelRowMap);
            }
            workbook.close();
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found please verify the file name");
        } catch (IOException e) {
            System.out.println("File not found please verify the file path");
        }
        return mapArray;
    }

    public static Map<String, String> getTestDataFromRow (String workBook, String sheetName, String testName){

        ArrayList<Map<String, String>> tableRows = getMapFromExcel(workBook,sheetName,testName);
        return tableRows.get(0);
    }

}
