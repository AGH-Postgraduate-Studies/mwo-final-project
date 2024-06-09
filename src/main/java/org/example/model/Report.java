package org.example.model;

import org.example.utils.ExcelReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        for (var l : getData(path)) {
            for (var o : l) {
                System.out.print("    " + o + "\t");
            }
            System.out.println();
        }
    }

    protected abstract List<List<Object>> getData(String path);

    protected abstract String getDescription();

    public void generate(String output) {
       switch (output) {
            case "console" -> printToConsole();
            case "pdf" -> System.out.println("Generating PDF file...");
            case "excel" -> System.out.println("Generating Excel file...");
            case "chart" -> System.out.println("Generating chart file...");
            default -> System.out.println("Wrong output provided");
        }
    }
}
