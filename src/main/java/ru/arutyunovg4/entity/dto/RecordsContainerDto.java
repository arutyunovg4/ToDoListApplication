package ru.arutyunovg4.entity.dto;

import ru.arutyunovg4.entity.Record;

import java.util.List;

public class RecordsContainerDto {
    private List<Record> records;
    private final int numberOfActiveRecords;
    private final int numberOfDoneRecords;

    public RecordsContainerDto(List<Record> records, int numberOfDoneRecords, int numberOfActiveRecords  ) {
        this.numberOfActiveRecords = numberOfActiveRecords;
        this.numberOfDoneRecords = numberOfDoneRecords;
        this.records = records;
    }

    public List<Record> getRecords() {
        return records;
    }

    public int getNumberOfActiveRecords() {
        return numberOfActiveRecords;
    }

    public int getNumberOfDoneRecords() {
        return numberOfDoneRecords;
    }
}
