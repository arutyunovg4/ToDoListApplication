package ru.arutyunovg4.entity.dto;

import ru.arutyunovg4.entity.Record;

import java.util.List;

public class RecordsContainerDto {
    private final String userName;
    private List<Record> records;
    private final int numberOfActiveRecords;
    private final int numberOfDoneRecords;

    public RecordsContainerDto(String userName, List<Record> records, int numberOfDoneRecords, int numberOfActiveRecords  ) {
        this.userName = userName;
        this.numberOfActiveRecords = numberOfActiveRecords;
        this.numberOfDoneRecords = numberOfDoneRecords;
        this.records = records;
    }

    public String getUserName() {
        return userName;
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
