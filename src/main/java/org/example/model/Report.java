package org.example.model;

import org.example.utils.ExcelPrinter;
import org.example.utils.ExcelReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Report {
    String path;
    List<Error> errorsList =  new ArrayList<>();

    public Report(String path) {
        this.path = path;
    }

    protected List<Collector> getRawData(String folderPath) {
        List<Collector> result = new ArrayList<>();
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            File[] listOfFolders = folder.listFiles();
            assert listOfFolders != null;
            for (File childFolder : listOfFolders) {
                result.addAll(getRawData(folderPath + "/" + childFolder.getName()));
            }
        } else {
            if (!folderPath.contains("~lock") && !folderPath.contains(".DS_Store")) {
                readFile(folderPath, result);
            }
        }
        return result;
    }

    private void readFile(String path, List<Collector> result) {
        ExcelReader.readExcel(path).forEach(result::addAll);
    }

    protected void printErrors() {
        if (errorsList != null && !errorsList.isEmpty()) {
            System.out.println();
            System.out.println("Errors");
            for (Error er : errorsList) {
                System.out.print("Error in file: " + er.getFileName() + " in sheet: " + er.getSheet());
                System.out.print(", in row: " + er.getRow() + ", in column: " + er.getColumn());
                System.out.print(" Error type is: " + er.getType());

                System.out.println();
            }
        }
    }

    public void printToConsole() {
        System.out.println(getDescription());
        for (var l : getData(path)) {
            for (var o : l) {
                System.out.print("    " + o + "\t");
            }
            System.out.println();
        }

        printErrors();
    }

    public void generateExcel() {
        System.out.println("Generating Excel file...");
        String exportPath = "data/output/report.xls";

        try {
            Files.createDirectories(Paths.get("data/output/"));
        } catch (IOException e) {
            System.out.println("Failed to create directories: " + e.getMessage());
            return;
        }

        ExcelPrinter printer = new ExcelPrinter(
                exportPath,
                "report.xls",
                "Report",
                "Kowalski_Jan.xls",
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        for (var l : getData(path)) {
            if (l.size() < 3) {
                System.err.println("Skipping row due to insufficient data: " + l);
                continue;
            }

            String task = l.get(1).toString();
            double time;
            try {
                time = Double.parseDouble(l.get(2).toString());
            } catch (NumberFormatException e) {
                System.err.println("Skipping row due to invalid time format: " + l);
                continue;
            }

            printer.addRow(task, time);
        }

        printer.setColumnWidth(new int[]{20, 30, 10});
        printer.save();
    }
    protected abstract List<List<Object>> getData(String path);

    protected abstract String getDescription();

    public void generate(String output) {
       switch (output) {
            case "console" -> printToConsole();
            case "pdf" -> System.out.println("Generating PDF file...");
            case "excel" -> generateExcel();
            case "chart" -> System.out.println("Generating chart file...");
            default -> System.out.println("Wrong output provided");
        }
    }
}
