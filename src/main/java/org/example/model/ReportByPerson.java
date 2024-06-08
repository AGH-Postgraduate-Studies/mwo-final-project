package org.example.model;

import org.example.utils.ExcelReader;

import java.io.File;
import java.util.*;

import static java.util.Arrays.asList;

public class ReportByPerson extends Report {
    public ReportByPerson(String path) {
        super(path);
    }

    protected List<List<Object>> getData(String path) {
        List<Collector> list = getRawData(path);
        Map<String, Double> map = new HashMap<>();
        for (Collector col : list) {
            for (WorkerTimeSheet ts : col.getWorkerTimeSheetList()) {
                if (map.containsKey(col.getDevName())) {
                    map.put(col.getDevName(), map.get(col.getDevName()) + ts.getTime());
                } else {
                    map.put(col.getDevName(), ts.getTime());
                }
            }
        }

        List<List<Object>> result = new ArrayList<>();
        map.forEach((k, v) -> result.add(asList(k, v)));

        return result;
    }

    @Override
    protected String getDescription() {
        return "Person - time report";
    }
}
