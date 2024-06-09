package org.example.model;

import org.example.utils.ExcelReader;
import org.example.utils.PdfGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Report {
    String path;

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

    public void printToConsole() {
        System.out.println(getDescription());
        for (List l : getData(path)) {
            for (Object o : l) {
                System.out.print("    " + o + "\t");
            }
            System.out.println();
        }
    }

    protected abstract List<List<Object>> getData(String path);

    protected abstract String getDescription();

    public void generate(String output) {
        if (output.equals("console")) {
            printToConsole();
        } else if (output.equals("pdf")) {
            getPdf();
        } else if (output.equals("excel")) {
            System.out.println("Generating Excel file...");
        } else if (output.equals("chart")) {
            System.out.println("Generating chart file...");
        } else {
            System.out.println("Wrong output provided");
        }
    }
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

}
