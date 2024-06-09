package org.example;

import org.example.model.*;
import org.example.utils.ExcelReader;
import org.example.utils.ExcelPrinter;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Report r1 = new ReportByTask("data/errors/negative_numbers/2012");
        r1.generate("console");
        System.out.println(" ");
        System.out.println("##############################");
        System.out.println(" ");
        Report r2 = new ReportByPerson("data/simple/2012");
        r2.generate("console");
        System.out.println(" ");
        System.out.println("##############################");
        System.out.println(" ");
        Report r3 = new ReportByProject("data/simple/2012");
        r3.generate("console");
        Report r4 = new ReportByPersonAdd("data/simple/2012");
        r4.generate("chart");

        String path;
        String type;
        String output;

        ExcelPrinter.test();

        try {
            path = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing file path. Add path");
            return;
        }

        try {
            type = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing report type. Add type");
            return;
        }

        try {
            output = args[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            output = "console";
        }

        Report report;

        switch (type) {
            case "1" -> report = new ReportByProject(path);
            case "2" -> report = new ReportByPerson(path);
            case "3" -> report = new ReportByTask(path);
            default -> {
                System.out.println("Wrong type provided");
                return;
            }
        }

        report.generate(output);
    }
}