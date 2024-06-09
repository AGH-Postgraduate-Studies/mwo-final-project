package org.example.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.example.model.Collector;
import org.example.model.Error;
import org.example.model.WorkerTimeSheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//
public class ExcelReader {

    private static boolean isCellEmpty(Cell cell) {
        return cell == null || cell.getCellType() == CellType.BLANK;
    }

    private static boolean isCellFilled(Cell cell) {
    return cell != null && cell.getCellType() != CellType.BLANK;
    }

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
                List<Error> errorsList = new ArrayList<>();
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

                        boolean hasError = false;
                        boolean isEmptyRow = false;

                        if (isCellEmpty(dateCell) && isCellEmpty(taskCell) && isCellEmpty(timeCell)){
                            isEmptyRow = true;
                        }

                        if (isCellFilled(dateCell))
                            if (DateUtil.isCellDateFormatted(dateCell)) {
                                builder.date(dateCell.getLocalDateTimeCellValue().toLocalDate());
                            } else {
                                Error er = new Error("wrong data format", path, sheetNo, i, 1);
                                hasError = true;
                                errorsList.add(er);
                            }
                        if (isCellFilled(taskCell)) {
                            builder.task(taskCell.getStringCellValue().trim());
                        }
                        if (isCellFilled(timeCell)) {
                            double time = timeCell.getNumericCellValue();
                            if (time < 0) {
                                Error er = new Error("negative number for time", path, sheetNo, i, 1);
                                hasError = true;
                                errorsList.add(er);
                            } else {
                                builder.time(timeCell.getNumericCellValue());
                            }
                        }

                        if (isCellEmpty(dateCell) && !isEmptyRow) {
                            Error er = new Error("empty cell", path, sheetNo, i, 1);
                            hasError = true;
                            errorsList.add(er);
                        }

                        if (isCellEmpty(taskCell) && !isEmptyRow) {
                            Error er = new Error("empty cell", path, sheetNo, i, 2);
                            errorsList.add(er);
                            hasError = true;
                        }

                        if (isCellEmpty(timeCell) && !isEmptyRow) {
                            Error er = new Error("empty cell", path, sheetNo, i, 3);
                            errorsList.add(er);
                            hasError = true;
                        }

                        if (!hasError && !isEmptyRow) {
                            workerTimeSheetList.add(builder.build());
                        }
                    }
                }

                collectorBuilder
                        .workerTimeSheetList(workerTimeSheetList)
                        .errorsList(errorsList)
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
