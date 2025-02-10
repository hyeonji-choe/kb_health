package org.study.kb_health.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class HealthEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_record_id", nullable = false)
    private HealthRecord healthRecord;

    @Column(nullable = false)
    private LocalDateTime periodFrom;

    @Column(nullable = false)
    private LocalDateTime periodTo;

    @Column(nullable = false)
    private String distanceUnit;

    @Column(nullable = false)
    private Double distanceValue;

    @Column(nullable = false)
    private String caloriesUnit;

    @Column(nullable = false)
    private Double caloriesValue;

    @Column(nullable = false)
    private Integer steps;
}
