package com.shahrozz.demo.DTOs;

import com.shahrozz.demo.DomainModels.ComplianceStandard;
import com.shahrozz.demo.DomainModels.SeverityLevel;
import com.shahrozz.demo.DomainModels.CloudProvider;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ComplianceRuleDto {
    private Long id;

    @NotBlank(message = "Rule ID is required")
    private String ruleId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Severity level is required")
    private SeverityLevel severityLevel;

    @NotNull(message = "Compliance standard is required")
    private ComplianceStandard standard;

    @NotNull(message = "Cloud provider is required")
    private CloudProvider cloudProvider;

    private String remediationSteps;
    private String resourceType;
}
