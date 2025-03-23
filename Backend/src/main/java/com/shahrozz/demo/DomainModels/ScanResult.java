package com.shahrozz.demo.DomainModels;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class ScanResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private CloudAccount account;

    private LocalDateTime scanStartTime;
    private LocalDateTime scanEndTime;

    @OneToMany(mappedBy = "scanResult", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Finding> findings = new HashSet<>();

    private int totalResourcesScanned;
    private int passedRulesCount;
    private int failedRulesCount;
    private int criticalFindings;
    private int highFindings;
    private int mediumFindings;
    private int lowFindings;
    private int infoFindings;

    @Enumerated(EnumType.STRING)
    private ComplianceStandard complianceStandard;
}