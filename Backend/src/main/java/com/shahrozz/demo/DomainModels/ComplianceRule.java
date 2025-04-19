package com.shahrozz.demo.DomainModels;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
public class ComplianceRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruleId;
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private SeverityLevel severityLevel;

    @Enumerated(EnumType.STRING)
    private ComplianceStandard standard;

    @Enumerated(EnumType.STRING)
    private CloudProvider cloudProvider;

    @Column(length = 2000)
    private String remediationSteps;

    private String resourceType;


}
