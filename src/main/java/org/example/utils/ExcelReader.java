package org.example.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.example.model.Collector;
import org.example.model.WorkerTimeSheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//
public class ExcelReader {

    public static List<List<Collector>> readExcel(String path) {
        List<List<Collector>> collectors = new ArrayList<>();
        List<Collector> collectorList = new ArrayList<>();

        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new HSSFWorkbook(fis);

            for(int sheetNo = 0; sheetNo < workbook.getNumberOfSheets(); sheetNo++) {
                Collector.CollectorBuilder collectorBuilder = Collector.builder();
                List<WorkerTimeSheet> workerTimeSheetList = new ArrayList<>();
                Sheet currSheet = workbook.getSheetAt(sheetNo);
                int rowCount = currSheet.getPhysicalNumberOfRows();

                collectorBuilder.devName(file.getName()
                        .replace("_", " ")
                        .replace(".xls", ""))
                        .proName(currSheet.getSheetName());

                for (int i = 1; i < rowCount; i++) {
                    Row row = currSheet.getRow(i);
                    WorkerTimeSheet.WorkerTimeSheetBuilder builder = WorkerTimeSheet.builder();
                    if (row != null) {

                        Cell dateCell = row.getCell(0);
                        Cell taskCell = row.getCell(1);
                        Cell timeCell = row.getCell(2);

                        if (dateCell != null && dateCell.getCellType() != CellType.BLANK) {
                            if (dateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
                                builder.date(dateCell.getLocalDateTimeCellValue().toLocalDate());
                            } else {
                                System.out.println("Cell without date: " + dateCell.toString());
                            }
                        }
                        if (taskCell != null)
                            builder.task(taskCell.getStringCellValue());
                        if (timeCell != null)
                            builder.time(timeCell.getNumericCellValue());

                        workerTimeSheetList.add(builder.build());
                    }
                }

                collectorBuilder
                        .workerTimeSheetList(workerTimeSheetList)
                        .build();
                collectorList.add(collectorBuilder.build());
            }
            collectors.add(collectorList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return collectors;
    }
}
