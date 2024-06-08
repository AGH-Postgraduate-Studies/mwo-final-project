package org.example.utils;

import org.example.model.WorkerTimeSheet;

import java.util.List;

public class ReportOneGenerator {
    public static void generateReportOne(List<WorkerTimeSheet> list, String path) {
        int hours = 0;

        for (WorkerTimeSheet workerTimeSheet : list) {
            hours += (int) workerTimeSheet.getTime();
        }

        if (list.isEmpty()) {
            System.out.println("Chosen list is empty.");
            System.out.println("Report can't be generated.");
        } else {
            System.out.println("Report one generated successfully.");
            System.out.println("Path to file: " +path);
            System.out.println("Total count hours in generated report: " + hours);
        }
    }
}
