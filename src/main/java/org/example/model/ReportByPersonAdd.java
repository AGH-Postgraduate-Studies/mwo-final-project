package org.example.model;

import java.util.*;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;


public class ReportByPersonAdd extends Report {
    public ReportByPersonAdd(String path) {
        super(path);
    }

    @Override
    public void printChart() {
        List<List<Object>> data = getData(this.path);
        List<String> people = new ArrayList<>();
        List<String> projects = new ArrayList<>();
        List<Double> totalTime = new ArrayList<>();

        for (List<Object> entry : data) {
            String category = (String) entry.get(0);
            String project = (String) entry.get(1);
            Double time = (Double) entry.get(2);

            people.add(category);
            projects.add(project);
            totalTime.add(time);
        }

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Total time based on person and his projects")
                .xAxisTitle("Person")
                .yAxisTitle("Time")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);

        for (int i = 0; i < people.size(); i++) {
            chart.addSeries(people.get(i) + " - " + projects.get(i),
                    Collections.singletonList(people.get(i)),
                    Collections.singletonList(totalTime.get(i)));
        }

        new SwingWrapper<>(chart).displayChart();
    }

    @Override
    protected List<List<Object>> getData(String path) {
            List<Collector> rawData = getRawData(this.path);
            Map<String, Map<String, Double>> summary = new HashMap<>();

            for (var x : rawData) {
                String devName = x.getDevName();
                String proName = x.getProName();
                double time = x.getWorkerTimeSheetList().stream()
                        .mapToDouble(WorkerTimeSheet::getTime)
                        .sum();

                summary.putIfAbsent(devName, new HashMap<>());
                Map<String, Double> devData = summary.get(devName);
                devData.put(proName, devData.getOrDefault(proName, 0.0) + time);
            }

            List<List<Object>> result = new ArrayList<>();
            summary.forEach((devName, proData) -> {
                proData.forEach((proName, time) -> {
                    result.add(Arrays.asList(devName, proName, time));
                });
            });

            return result;
        }

    @Override
    protected String getDescription() {
        return null;
    }
}
