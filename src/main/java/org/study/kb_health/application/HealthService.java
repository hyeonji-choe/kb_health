package org.study.kb_health.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.study.kb_health.domain.entity.HealthEntry;
import org.study.kb_health.domain.entity.HealthRecord;
import org.study.kb_health.repository.HealthEntryJpaRepository;
import org.study.kb_health.repository.HealthRecordJpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HealthService {
    @Autowired
    private HealthRecordJpaRepository healthRecordRepository;

    @Autowired
    private HealthEntryJpaRepository healthEntryRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public HealthRecord saveHealthRecord(HealthRecord record) {
        kafkaTemplate.send("health_topic", record.getRecordKey());
        redisTemplate.opsForValue().set(record.getRecordKey(), "processed");
        return healthRecordRepository.save(record);
    }

    public HealthRecord getHealthRecord(Long id) {
        return healthRecordRepository.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));
    }

    public List<HealthEntry> getDailyRecords(String recordKey, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return healthEntryRepository.findByHealthRecord_RecordKeyAndPeriodFromBetween(recordKey, startOfDay, endOfDay);
    }

    public List<HealthEntry> getMonthlyRecords(String recordKey, int year, int month) {
        LocalDateTime startOfMonth = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime endOfMonth = LocalDate.of(year, month, startOfMonth.toLocalDate().lengthOfMonth()).atTime(23, 59, 59);
        return healthEntryRepository.findByHealthRecord_RecordKeyAndPeriodFromBetween(recordKey, startOfMonth, endOfMonth);
    }

}
