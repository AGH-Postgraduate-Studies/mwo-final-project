package org.example.model;

import java.util.*;

public class ReportByPersonAdd extends Report {
    public ReportByPersonAdd(String path) {
        super(path);
    }

    @Override
    protected List<List<Object>> getData(String path) {
        List<Collector> rawData = getRawData(this.path);
        Map<String, Map<String, Double>> summary = new HashMap<>();

        for(var x : rawData) {
            List<WorkerTimeSheet> workerTimeSheets = x.getWorkerTimeSheetList();
            summary.putIfAbsent(x.getDevName(), new HashMap<>());
            Map<String, Double> totalTimeMap = summary.get(x.getDevName());

            double totalTime = workerTimeSheets.stream()
                    .mapToDouble(WorkerTimeSheet::getTime)
                    .sum();
            totalTimeMap.put(x.getProName(), totalTimeMap.getOrDefault(x.getProName(), 0.0) + totalTime);
        }

        List<List<Object>> result = new ArrayList<>();
        summary.forEach((k,v) -> result.add(Arrays.asList(k,v)));
        return result;
    }

    @Override
    protected String getDescription() {
        return null;
    }
}
