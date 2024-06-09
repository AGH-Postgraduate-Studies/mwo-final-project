package org.example.model;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import java.util.*;

import static java.util.Arrays.asList;

public class ReportByTask extends Report {
    public ReportByTask(String path) {
        super(path);
    }

    @Override
    public void printChart() {
        List<List<Object>> auxPersonTimeListList = getData(this.path);
        List<String> tasks = new ArrayList<>();
        List<Double> totalTime = new ArrayList<>();

        for(var entry : auxPersonTimeListList) {
            tasks.add(String.valueOf(entry.get(0)));
            totalTime.add((Double) entry.get(1));
        }

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Tasks Total Time")
                .xAxisTitle("Task")
                .yAxisTitle("Total Time")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);

        chart.addSeries("Task - Total time",
                tasks,
                totalTime);

        new SwingWrapper<>(chart).displayChart();
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

            if (col.getErrorsList() != null) {
                this.errorsList.addAll(col.getErrorsList());
            }
        }

        List<List<Object>> result = new ArrayList<>();
        map.forEach((k, v) -> result.add(asList(k, v)));

        result.sort((l1, l2) -> (int) ((Double) l2.get(1) - (Double) l1.get(1)));

        return result;
    }

    @Override
    protected String getDescription() {
        return "Task by description - time report";
    }
}
