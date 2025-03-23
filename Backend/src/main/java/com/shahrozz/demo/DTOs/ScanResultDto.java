package com.shahrozz.demo.DTOs;
import com.shahrozz.demo.DomainModels.CloudProvider;
import com.shahrozz.demo.DomainModels.ComplianceStandard;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScanResultDto {
    private Long id;
    private Long accountId;
    private String accountName;
    private CloudProvider provider;
    private LocalDateTime scanStartTime;
    private LocalDateTime scanEndTime;
    private int totalResourcesScanned;
    private int passedRulesCount;
    private int failedRulesCount;
    private int criticalFindings;
    private int highFindings;
    private int mediumFindings;
    private int lowFindings;
    private int infoFindings;
    private ComplianceStandard complianceStandard;
    private double complianceScore;
}