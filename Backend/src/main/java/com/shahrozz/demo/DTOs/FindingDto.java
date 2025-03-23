package com.shahrozz.demo.DTOs;

import com.shahrozz.demo.DomainModels.SeverityLevel;
import lombok.Data;

@Data
public class FindingDto {
    private Long id;
    private String ruleId;
    private String ruleTitle;
    private String resourceId;
    private String resourceName;
    private String resourceType;
    private SeverityLevel severityLevel;
    private String details;
    private boolean compliant;
    private String remediationSteps;
}
