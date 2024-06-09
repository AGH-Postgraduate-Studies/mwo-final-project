package org.example.model;

import org.example.utils.ExcelReader;
import org.example.utils.PdfGenerator;

import java.io.File;
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

    protected abstract List<List<Object>> getData(String path);

    protected abstract String getDescription();

    protected void getPdf()
    {
        StringBuilder pdfBody = new StringBuilder(" ");

        pdfBody.append(getDescription());
        for (List l : getData(path)) {
            for (Object o : l) {
               pdfBody.append("    ").append(o).append(" ");
            }
        }
    PdfGenerator.generatePdf(getDescription(),getData(path));
    }

       switch (output) {
            case "console" -> printToConsole();
            case "pdf" -> System.out.println("Generating PDF file...");
            case "excel" -> System.out.println("Generating Excel file...");
            case "chart" -> System.out.println("Generating chart file...");
            default -> System.out.println("Wrong output provided");
        }
    }

}
