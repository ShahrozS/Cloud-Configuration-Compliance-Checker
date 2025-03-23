package com.shahrozz.demo.DTOs;

import com.shahrozz.demo.DomainModels.ComplianceStandard;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class ScanRequestDto {
    @NotNull(message = "Account ID is required")
    private Long accountId;

    @NotNull(message = "Compliance standard is required")
    private ComplianceStandard standard;

    private List<String> resourceTypes;
    private boolean includePassedRules = true;
}