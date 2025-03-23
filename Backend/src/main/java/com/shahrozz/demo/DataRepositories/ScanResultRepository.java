package com.shahrozz.demo.DataRepositories;

import com.shahrozz.demo.DomainModels.CloudAccount;
import com.shahrozz.demo.DomainModels.ComplianceStandard;
import org.hibernate.boot.archive.scan.spi.ScanResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScanResultRepository extends JpaRepository<ScanResult, Long> {
    List<ScanResult> findByAccount(CloudAccount account);
    List<ScanResult> findByAccountAndComplianceStandard(CloudAccount account, ComplianceStandard standard);
    List<ScanResult> findByAccountAndScanStartTimeBetween(CloudAccount account, LocalDateTime start, LocalDateTime end);
}