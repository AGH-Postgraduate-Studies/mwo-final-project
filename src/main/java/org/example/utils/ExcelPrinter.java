package org.example.utils;

import lombok.Getter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.time.LocalDate;

public class ExcelPrinter {
    @Getter
    private final String exportPath;
    @Getter
    private final String fileName;
    @Getter
    private final String title;
    @Getter
    private final String person;
    @Getter
    private final LocalDate dateFrom;
    @Getter
    private final LocalDate dateTo;
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private int rowIndex = 0;

    public ExcelPrinter(String exportPath, String fileName, String title, String person, LocalDate dateFrom,
                        LocalDate dateTo) {
        this.exportPath = exportPath;
        this.fileName = fileName;
        this.title = title;
        this.person = person;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.workbook = new HSSFWorkbook();
        this.sheet = workbook.createSheet(title);
    }

    public void addRow(Object[] array) {
        HSSFRow row = sheet.createRow(rowIndex++);

        for (int i = 0; i < array.length; i++) {
            Object element = array[i];
            switch (element.getClass().getSimpleName()) {
                case "String":
                    row.createCell(i).setCellValue((String) element);
                    break;
                case "Double":
                    row.createCell(i).setCellValue((Double) element);
                    break;
                case "Integer":
                    row.createCell(i).setCellValue((Integer) element);
                    break;
                case "LocalDate":
                    row.createCell(i).setCellValue(element.toString());
                    break;
                default:
                    row.createCell(i).setCellValue(element != null ? element.toString() : "null");
                    break;
            }
        }

    }

    public void setColumnWidth(int[] width) {
        for (int i = 0; i < width.length; i++) {
            sheet.setColumnWidth(i, width[i] * 256);
        }
    }

    public void save() {
        try {
            try (FileOutputStream fileOutputStream = new FileOutputStream(exportPath + fileName)) {
                workbook.write(fileOutputStream);
            }
            System.out.println("Workbook saved as: " + fileName);
        } catch (Exception e) {
            System.out.println("Error while saving workbook: " + e.getMessage());
        }
    }
}
