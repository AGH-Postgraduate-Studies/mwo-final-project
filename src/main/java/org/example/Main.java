package org.example;

import java.util.Arrays;

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

        System.out.println("Generate report for path: " + path);
        System.out.println("Report type: " + type);
        System.out.println("Output: " + output);
    }
}