package org.example.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.example.model.WorkerTimeSheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    public static List<WorkerTimeSheet> readExcel(String path) {
        List<WorkerTimeSheet> workerTimeSheetList = new ArrayList<>();

        try (
                FileInputStream fis = new FileInputStream(path);
                Workbook workbook = new HSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            String sheetName = workbook.getSheetName(0);

            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    WorkerTimeSheet.WorkerTimeSheetBuilder builder = WorkerTimeSheet.builder();

                    Cell dateCell = row.getCell(0);
                    Cell taskCell = row.getCell(1);
                    Cell timeCell = row.getCell(2);

                    if (dateCell != null)
                        if (DateUtil.isCellDateFormatted(dateCell))
                            builder.date(dateCell.getLocalDateTimeCellValue().toLocalDate());
                    if (taskCell != null)
                        builder.task(taskCell.getStringCellValue());
                    if (timeCell != null)
                        builder.time(timeCell.getNumericCellValue());

                    workerTimeSheetList.add(builder.build());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return workerTimeSheetList;
    }
}