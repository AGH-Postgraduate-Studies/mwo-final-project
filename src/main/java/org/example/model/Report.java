package org.example.model;

import org.example.utils.ExcelPrinter;
import org.example.utils.ExcelReader;
import org.example.utils.PdfGenerator;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Report {
    String path;
    List<Error> errorsList = new ArrayList<>();

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
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println();

            System.out.println("Errors found in the following files: ");
            for (Error er : errorsList) {
                System.out.print("Error in file: " + er.getFileName() + " in sheet: " + er.getSheet());
                System.out.print(", in row: " + er.getRow() + ", in column: " + er.getColumn());
                System.out.print(" Error type is: " + er.getType());
                System.out.println();
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println();

        }
    }

    public void printToConsole() {
        Date date = new java.util.Date();
        System.out.println();

        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println(getDescription());
        System.out.println("-------------------------------------------------------------------------------------");

        System.out.println("Report generated " + date );
        System.out.println();
        for (var l : getData(path)) {
            for (var o : l) {
                System.out.print("    " + o + "\t");
            }
            System.out.println();
        }
        printErrors();
    }

    public void generateExcelFromData() {
        String exportPath = "data/output/";
        LocalDateTime.now();
        String fileName = "report_" + LocalDateTime.now().toString().replace(":", "-") + ".xls";
        String title = getDescription();
        LocalDate dateFrom = LocalDate.of(2012, 1, 1);
        LocalDate dateTo = LocalDate.now().plusDays(1);

        ExcelPrinter printer = new ExcelPrinter(
                exportPath,
                fileName,
                title,
                dateFrom,
                dateTo
        );

        List<List<Object>> data = getData(path);
        for (List<Object> row : data) {
            printer.addRow(row.toArray());
        }

        printer.setColumnWidth(new int[]{20, 30, 10});
        printer.save();

        printErrors();
    }

    public abstract void printChart();

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
    public void generate(String output) {
        switch (output) {
            case "console" -> printToConsole();
            case "pdf" -> getPdf();
            case "excel" -> generateExcelFromData();
            case "chart" -> printChart();
            default -> System.out.println("Wrong output provided");
        }
    }

}
