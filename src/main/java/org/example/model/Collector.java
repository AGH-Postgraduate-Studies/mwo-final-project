package org.example.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class Collector {
    private String devName;
    private String proName;
    private List<WorkerTimeSheet> workerTimeSheetList;
}
