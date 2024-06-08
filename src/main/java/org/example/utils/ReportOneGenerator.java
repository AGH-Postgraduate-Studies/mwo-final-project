package org.example.utils;

import org.example.model.WorkerTimeSheet;

import java.io.File;
import java.util.List;

public class ReportOneGenerator {

    public static void generateReportOne(String folderPath) {
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            File[] listOfFolders = folder.listFiles();
            assert listOfFolders != null;
            for (File childFolder : listOfFolders)
            {
                generateReportOne(folderPath + "/" + childFolder.getName());
            }
        } else {
            ExcelReader.readExcel(folderPath);
            List<WorkerTimeSheet> list = ExcelReader.readExcel(folderPath);
            ReportOneGenerator.getHoursFromFile((List<WorkerTimeSheet>) list, folderPath);
        }
    }

    public static void getHoursFromFile(List<WorkerTimeSheet> list, String path) {
        int hours = 0;

        for (WorkerTimeSheet workerTimeSheet : list) {
            hours += (int) workerTimeSheet.getTime();
        }

        if (list.isEmpty()) {
            System.out.println("Chosen list is empty.");
            System.out.println("Report can't be generated.");
        } else {
//            System.out.println("Report one generated successfully.");
//
//            System.out.println("Path to file: " +path);

            System.out.println("Total count hours in " + "path to file: " + path + " is: " + hours);
        }
    }
}
