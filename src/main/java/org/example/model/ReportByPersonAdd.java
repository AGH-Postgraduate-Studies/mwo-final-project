package org.example.model;

import java.util.List;

public class ReportByPersonAdd extends Report {
    public ReportByPersonAdd(String path) {
        super(path);
    }

    @Override
    protected List<List<Object>> getData(String path) {
        return null;
    }

    @Override
    protected String getDescription() {
        return null;
    }
}
