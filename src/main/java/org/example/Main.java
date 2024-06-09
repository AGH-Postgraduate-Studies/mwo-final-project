package org.example;

import org.example.model.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

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