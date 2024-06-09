package org.example.utils;

import lombok.Getter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

        //initializeWorkbook();
    }

    public static void test() {
        String exportPath = "data/simple/2012/01/report.xls";
        try {
            Files.createDirectories(Paths.get("data/simple/2012/01/"));
        } catch (IOException e) {
            System.out.println("Failed to create directories: " + e.getMessage());
            return;
        }

        ExcelPrinter printer = new ExcelPrinter(exportPath,
                "report.xls", "Report", "Kowalski_Jan.xls",
                LocalDate.of(2012, 1, 1),
                LocalDate.of(2012, 1, 31));

        printer.addRow("test", 1.0);
        printer.addRow("test", 2.0);

        printer.setColumnWidth(new int[]{10, 10, 10});

        printer.save();
    }

    private void initializeWorkbook() {
        try {
            InputStream inputStream = ExcelPrinter.class.getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }

            workbook = new HSSFWorkbook(inputStream);
            sheet = workbook.getSheetAt(0);
            inputStream.close();
        } catch (Exception e) {
            System.out.println("Error while initializing workbook: " + e.getMessage());
        }
    }

    public void addRow(String task, double time) {
        rowIndex++;
        HSSFRow row = sheet.createRow(rowIndex);
        row.createCell(0).setCellValue(dateFrom.toString());
        row.createCell(1).setCellValue(task);
        row.createCell(2).setCellValue(time);
    }

    public void setColumnWidth(int[] width) {
        for (int i = 0; i < width.length; i++) {
            sheet.setColumnWidth(i, width[i] * 256);
        }
    }

    public void save() {
        try {
            try (FileOutputStream fileOutputStream = new FileOutputStream(exportPath)) {
                workbook.write(fileOutputStream);
                fileOutputStream.flush();
            }
            System.out.println("Workbook saved as: " + exportPath);
        } catch (Exception e) {
            System.out.println("Error while saving workbook: " + e.getMessage());
        }
    }
}
