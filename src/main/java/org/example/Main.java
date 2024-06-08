package org.example;

import org.example.model.Collector;
import org.example.model.WorkerTimeSheet;
import org.example.utils.ExcelReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        List<List<Collector>> list = ExcelReader.readExcel("data/simple/2012/01/Kowalski_Jan.xls");
        for(var x : list) {
            for(var y : x) {
                System.out.println(y);
            }
            System.out.println();
        }
    }
}