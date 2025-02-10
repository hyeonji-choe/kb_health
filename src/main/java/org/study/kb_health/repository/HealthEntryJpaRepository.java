package org.study.kb_health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.study.kb_health.domain.entity.HealthEntry;

import java.time.LocalDateTime;
import java.util.List;

public interface HealthEntryJpaRepository extends JpaRepository<HealthEntry, Long> {
    List<HealthEntry> findByHealthRecord_RecordKeyAndPeriodFromBetween(String recordKey, LocalDateTime start, LocalDateTime end);
}
