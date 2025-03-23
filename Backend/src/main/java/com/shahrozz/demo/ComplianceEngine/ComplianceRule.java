package com.shahrozz.demo.ComplianceEngine;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComplianceRule {
    private String id;
    private String title;
    private String description;
    private String framework;  // CIS, NIST, ISO27001
    private String cloudProvider; // AWS, GCP, Azure
    private String service;    // EC2, S3, VPC
    private String checkMethod;
    private String severity;   // HIGH, MEDIUM, LOW
}