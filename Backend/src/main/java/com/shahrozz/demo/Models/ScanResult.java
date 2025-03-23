package com.shahrozz.demo.Models;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ScanResult {
    @Id
    @GeneratedValue
    private Long id;
    private String cloudProvider;
    private LocalDateTime timestamp;
    private String complianceStandard;
    private String results;
}

