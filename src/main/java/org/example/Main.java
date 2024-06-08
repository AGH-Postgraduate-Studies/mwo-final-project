package org.example;

import org.example.model.*;
import org.example.utils.ExcelReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<List<Collector>> list = ExcelReader.readExcel("data/simple/2012/01/Kowalski_Jan.xls");
        System.out.println("Database {");
        for(var x : list) {
            for(var y : x) {
                System.out.println("\t" + y);
            }
        }
        System.out.println("}");

        String path;
        String type;
        String output;

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

        if (type.equals("1")) {
            report = new ReportByProject(path);
        } else if (type.equals("2")) {
            report = new ReportByPerson(path);
        } else if (type.equals("3")) {
            report = new ReportByTask(path);
        } else {
            System.out.println("Wrong type provided");
            return;
        }

        report.generate(output);

    }
}