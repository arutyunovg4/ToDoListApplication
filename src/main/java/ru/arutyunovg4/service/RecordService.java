package ru.arutyunovg4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arutyunovg4.entity.Record;
import ru.arutyunovg4.entity.RecordStatus;
import ru.arutyunovg4.entity.dto.RecordsContainerDto;
import ru.arutyunovg4.repository.RecordRepository;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class RecordService {
    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Transactional(readOnly = true)
    public RecordsContainerDto findAllRecords(String filterMode) {

        List<Record> records = recordRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        int numberOfDoneRecords = (int) records.stream().filter(r -> r.getStatus() == RecordStatus.DONE).count();
        int numberOfActiveRecords = (int) records.stream().filter(r -> r.getStatus() == RecordStatus.ACTIVE).count();

        if (filterMode == null || filterMode.isBlank())
            return new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);

        String filterModeInUpperCase = filterMode.toUpperCase();
        List<String> allowedFilterModes = Arrays.stream(RecordStatus.values())
                .map(Enum::name)
                .toList();

        if (allowedFilterModes.contains(filterModeInUpperCase)) {

            List<Record> filteredRecords = records.stream()
                    .filter(record -> record.getStatus() == RecordStatus.valueOf(filterModeInUpperCase))
                    .toList();
            return new RecordsContainerDto(filteredRecords, numberOfDoneRecords, numberOfActiveRecords);
        } else {
            return new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);
        }

    }

    public void saveRecord(String title) {
        if (title != null && !title.isBlank()) {
            recordRepository.save(new Record(title));
        }
    }

    public void updateRecordStatus(int id, RecordStatus newStatus) {
        recordRepository.update(id, newStatus);
    }

    public void deleteRecord(int id) {
        recordRepository.deleteById(id);
    }
}
