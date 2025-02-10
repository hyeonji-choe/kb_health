package org.study.kb_health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.study.kb_health.domain.entity.HealthRecord;

public interface HealthRecordJpaRepository extends JpaRepository<HealthRecord, Long> {}