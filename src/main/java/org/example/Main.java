package org.example;

import org.example.model.WorkerTimeSheet;
import org.example.utils.ExcelReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        List<?> list = ExcelReader.readExcel("data/simple/2012/01/Nowak_Piotr.xls");
        list.forEach(x -> System.out.println(x + " "));
    }
}