package com.shahrozz.demo.ComplianceEngine;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ComplianceResult {
    private String framework;
    private LocalDateTime timestamp;
    private List<ComplianceCheckResult> checks = new ArrayList<>();

    public void addCheck(ComplianceRule rule, boolean status) {
        checks.add(new ComplianceCheckResult(rule, status));
    }

    @Data
    @AllArgsConstructor
    public static class ComplianceCheckResult {
        private ComplianceRule rule;
        private boolean compliant;
    }
}