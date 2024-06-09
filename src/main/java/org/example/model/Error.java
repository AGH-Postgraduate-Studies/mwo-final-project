package org.example.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
public class Error {
    private String type;
    private String fileName;
    private int Sheet;
    private int Row;
    private int Column;

    public Error(String type, String fileName, int sheet, int row, int column) {
        this.type = type;
        this.fileName = fileName;
        Sheet = sheet;
        Row = row;
        Column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSheet() {
        return Sheet;
    }

    public void setSheet(int sheet) {
        Sheet = sheet;
    }

    public int getRow() {
        return Row;
    }

    public void setRow(int row) {
        Row = row;
    }

    public int getColumn() {
        return Column;
    }

    public void setColumn(int column) {
        Column = column;
    }
}
