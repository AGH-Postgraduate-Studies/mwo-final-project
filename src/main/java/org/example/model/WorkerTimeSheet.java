package org.example.model;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class WorkerTimeSheet {
    private LocalDate date;
    private String task;
    private float time;
}
