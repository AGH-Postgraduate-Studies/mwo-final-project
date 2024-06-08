package org.example;

import org.example.model.Collector;
import org.example.model.WorkerTimeSheet;
import org.example.utils.ExcelReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<?> list = ExcelReader.readExcel("data/simple/2012/01/Nowak_Piotr.xls");
        list.forEach(x -> System.out.println(x + " "));
      
         List<List<Collector>> list = ExcelReader.readExcel("data/simple/2012/01/Kowalski_Jan.xls");
        for(var x : list) {
            for(var y : x) {
                System.out.println(y);
            }
            System.out.println();
        }
      
        new WorkerTimeSheet();
      
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

        System.out.println("Generate report for path: " + path);
        System.out.println("Report type: " + type);
        System.out.println("Output: " + output);
    }
}