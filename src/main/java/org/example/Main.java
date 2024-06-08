package org.example;

import org.example.model.WorkerTimeSheet;
import org.example.utils.ExcelReader;
import org.example.utils.ReportOneGenerator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String path = "data/simple/2012";

        ReportOneGenerator.generateReportOne(path);
    }
}
