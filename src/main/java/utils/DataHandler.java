package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DataHandler {

        public static Map<String, Map<String, String>> getExcelData(InputStream stream, String sheetName) {
            Map<String, Map<String, String>> sheetMap = new HashMap<>();

            try (Workbook workbook = new XSSFWorkbook(stream)) {
                Sheet sheet = workbook.getSheet(sheetName);
                Row headerRow = sheet.getRow(0);

                // Loop through each data row (skipping the header row 0)
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row currentRow = sheet.getRow(i);
                    if (currentRow == null || currentRow.getCell(0) == null) continue;

                    String testCaseId = currentRow.getCell(0).getStringCellValue().trim();
                    Map<String, String> rowDataMap = new HashMap<>();

                    // Map every column cell value to its matching header text name
                    for (int j = 1; j < headerRow.getPhysicalNumberOfCells(); j++) {
                        String headerName = headerRow.getCell(j).getStringCellValue().trim();
                        DataFormatter formatter = new DataFormatter();
                        String cellValue = formatter.formatCellValue(currentRow.getCell(j));

                        rowDataMap.put(headerName, cellValue);
                    }
                    sheetMap.put(testCaseId, rowDataMap);
                }
            } catch (Exception e) {
                throw new RuntimeException("Fatal failure loading target spreadsheet stream matrix maps", e);
            }
            return sheetMap;
        }
}