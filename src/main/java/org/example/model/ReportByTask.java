package org.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class ReportByTask extends Report {
    public ReportByTask(String path) {
        super(path);
    }

    protected List<List<Object>> getData(String path) {
        List<Collector> list = getRawData(path);
        Map<String, Double> map = new HashMap<>();
        for (Collector col : list) {
            for (WorkerTimeSheet ts : col.getWorkerTimeSheetList()) {
                if (map.containsKey(ts.getTask())) {
                    map.put(ts.getTask(), map.get(ts.getTask()) + ts.getTime());
                } else {
                    map.put(ts.getTask(), ts.getTime());
                }
            }
        }

        List<List<Object>> result = new ArrayList<>();
        map.forEach((k, v) -> result.add(asList(k, v)));

        return result;
    }

    @Override
    protected String getDescription() {
        return "Task by description - time report";
    }
}