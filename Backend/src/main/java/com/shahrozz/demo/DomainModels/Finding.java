package com.shahrozz.demo.DomainModels;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Finding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "scan_result_id")
    private ScanResult scanResult;

    @ManyToOne
    @JoinColumn(name = "rule_id")
    private ComplianceRule rule;

    private String resourceId;
    private String resourceName;
    private String resourceType;

    @Enumerated(EnumType.STRING)
    private SeverityLevel severityLevel;

    @Column(length = 2000)
    private String details;

    private boolean compliant;

    @Column(length = 2000)
    private String remediationSteps;
}
