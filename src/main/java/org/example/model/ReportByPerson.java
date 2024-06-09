package org.example.model;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import java.util.*;

import static java.util.Arrays.asList;

public class ReportByPerson extends Report {
    public ReportByPerson(String path) {
        super(path);
    }

    @Override
    public void printChart() {
        List<List<Object>> auxPersonTimeListList = getData(this.path);
        List<String> person = new ArrayList<>();
        List<Double> totalTime = new ArrayList<>();

        for(var entry : auxPersonTimeListList) {
            person.add(String.valueOf(entry.get(0)));
            totalTime.add((Double) entry.get(1));
        }
        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("People Total times")
                .xAxisTitle("Person")
                .yAxisTitle("Total Time")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);

        chart.addSeries("Person - Total time",
                person,
                totalTime);

        new SwingWrapper<>(chart).displayChart();
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

            if (col.getErrorsList() != null) {
                this.errorsList.addAll(col.getErrorsList());
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
