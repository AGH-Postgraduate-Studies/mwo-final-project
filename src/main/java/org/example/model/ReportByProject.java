package org.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class ReportByProject extends Report {
    public ReportByProject(String path) {
        super(path);
    }

    protected List<List<Object>> getData(String path) {
        List<Collector> list = getRawData(path);
        Map<String, Double> map = new HashMap<>();
        for (Collector col : list) {
            for (WorkerTimeSheet ts : col.getWorkerTimeSheetList()) {
                if (map.containsKey(col.getProName())) {
                    map.put(col.getProName(), map.get(col.getProName()) + ts.getTime());
                } else {
                    map.put(col.getProName(), ts.getTime());
                }
            }
        }

        List<List<Object>> result = new ArrayList<>();
        map.forEach((k, v) -> result.add(asList(k, v)));

        return result;
    }

    @Override
    protected String getDescription() {
        return "Project - time report";
    }
}
