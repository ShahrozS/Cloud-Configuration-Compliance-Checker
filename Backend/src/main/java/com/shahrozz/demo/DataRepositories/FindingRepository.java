package com.shahrozz.demo.DataRepositories;


import com.shahrozz.demo.DomainModels.Finding;
import com.shahrozz.demo.DomainModels.ScanResult;
import com.shahrozz.demo.DomainModels.SeverityLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FindingRepository extends JpaRepository<Finding, Long> {
    List<Finding> findByScanResult(ScanResult scanResult);
    List<Finding> findByScanResultAndCompliant(ScanResult scanResult, boolean compliant);
    List<Finding> findByScanResultAndSeverityLevel(ScanResult scanResult, SeverityLevel severityLevel);
    long countByScanResultAndCompliant(ScanResult scanResult, boolean compliant);
}