package org.study.kb_health;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.study.kb_health.application.HealthService;
import org.study.kb_health.domain.entity.HealthEntry;
import org.study.kb_health.domain.entity.HealthRecord;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/health")
class HealthController {
    @Autowired
    private HealthService healthService;

    @PostMapping("/record")
    public HealthRecord saveHealthRecord(@RequestBody HealthRecord record) {
        return healthService.saveHealthRecord(record);
    }

    @GetMapping("/record/{id}")
    public HealthRecord getHealthRecord(@PathVariable Long id) {
        return healthService.getHealthRecord(id);
    }

    @GetMapping("/record/daily/{recordKey}")
    public List<HealthEntry> getDailyRecords(@PathVariable String recordKey, @RequestParam LocalDate date) {
        return healthService.getDailyRecords(recordKey, date);
    }

    @GetMapping("/record/monthly/{recordKey}")
    public List<HealthEntry> getMonthlyRecords(@PathVariable String recordKey, @RequestParam int year, @RequestParam int month) {
        return healthService.getMonthlyRecords(recordKey, year, month);
    }
}
