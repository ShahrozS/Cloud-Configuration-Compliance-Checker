package com.shahrozz.demo.Scanner;

import com.shahrozz.demo.DTOs.FindingDto;
import com.shahrozz.demo.DTOs.ScanRequestDto;
import com.shahrozz.demo.DTOs.ScanResultDto;
import com.shahrozz.demo.DataRepositories.CloudAccountRepository;
import com.shahrozz.demo.DataRepositories.ComplianceRuleRepository;
import com.shahrozz.demo.DataRepositories.FindingRepository;
import com.shahrozz.demo.DataRepositories.ScanResultRepository;
import com.shahrozz.demo.DomainModels.*;
import com.shahrozz.demo.Services.CloudComplianceService;
import com.shahrozz.demo.Services.CloudProviderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScanService {
    private final CloudAccountRepository cloudAccountRepository;
    private final ComplianceRuleRepository complianceRuleRepository;
    private final ScanResultRepository scanResultRepository;
    private final FindingRepository findingRepository;
    private final CloudProviderFactory cloudProviderFactory;

    @Transactional
    public ScanResultDto performScan(ScanRequestDto scanRequestDto) {
        // Get the cloud account
        CloudAccount account = cloudAccountRepository.findById(scanRequestDto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + scanRequestDto.getAccountId()));

        // Get compliance rules for the standard and provider
        List<ComplianceRule> rules = complianceRuleRepository.findByStandardAndCloudProvider(
                scanRequestDto.getStandard(), account.getProvider());

        // Filter rules by resource types if provided
        if (scanRequestDto.getResourceTypes() != null && !scanRequestDto.getResourceTypes().isEmpty()) {
            rules = rules.stream()
                    .filter(rule -> scanRequestDto.getResourceTypes().contains(rule.getResourceType()))
                    .collect(Collectors.toList());
        }

        // Start the scan
        LocalDateTime scanStartTime = LocalDateTime.now();

        // Get the appropriate cloud provider service
        CloudComplianceService cloudService = cloudProviderFactory.getProviderService(account);

        // Evaluate compliance
        List<Finding> findings = cloudService.evaluateCompliance(account, rules);

        // Create scan result
        ScanResult scanResult = new ScanResult();
        scanResult.setAccount(account);
        scanResult.setScanStartTime(scanStartTime);
        scanResult.setScanEndTime(LocalDateTime.now());
        scanResult.setComplianceStandard(scanRequestDto.getStandard());
        scanResult.setTotalResourcesScanned(cloudService.getTotalResourceCount(account));

        // Count findings by severity and compliance
        int compliantCount = 0;
        int nonCompliantCount = 0;
        Map<SeverityLevel, Integer> severityCounts = new java.util.HashMap<>(Map.of(
                SeverityLevel.CRITICAL, 0,
                SeverityLevel.HIGH, 0,
                SeverityLevel.MEDIUM, 0,
                SeverityLevel.LOW, 0,
                SeverityLevel.INFO, 0
        ));

        for (Finding finding : findings) {
            if (finding.isCompliant()) {
                compliantCount++;
            } else {
                nonCompliantCount++;
                SeverityLevel severity = finding.getSeverityLevel();
                severityCounts.put(severity, severityCounts.get(severity) + 1);
            }

            // Associate finding with scan result
            finding.setScanResult(scanResult);
        }

        scanResult.setPassedRulesCount(compliantCount);
        scanResult.setFailedRulesCount(nonCompliantCount);
        scanResult.setCriticalFindings(severityCounts.get(SeverityLevel.CRITICAL));
        scanResult.setHighFindings(severityCounts.get(SeverityLevel.HIGH));
        scanResult.setMediumFindings(severityCounts.get(SeverityLevel.MEDIUM));
        scanResult.setLowFindings(severityCounts.get(SeverityLevel.LOW));
        scanResult.setInfoFindings(severityCounts.get(SeverityLevel.INFO));

        // Save scan result and findings
        ScanResult savedScanResult = scanResultRepository.save(scanResult);
        findingRepository.saveAll(findings);

        return convertToDto(savedScanResult);
    }

    public List<ScanResultDto> getAllScanResults() {
        return scanResultRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ScanResultDto getScanResultById(Long id) {
        ScanResult scanResult = scanResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scan result not found with id: " + id));
        return convertToDto(scanResult);
    }

    public List<FindingDto> getFindings(Long scanResultId, boolean onlyFailed) {
        ScanResult scanResult = scanResultRepository.findById(scanResultId)
                .orElseThrow(() -> new RuntimeException("Scan result not found with id: " + scanResultId));

        List<Finding> findings;
        if (onlyFailed) {
            findings = findingRepository.findByScanResultAndCompliant(scanResult, false);
        } else {
            findings = findingRepository.findByScanResult(scanResult);
        }

        return findings.stream()
                .map(this::convertToFindingDto)
                .collect(Collectors.toList());
    }

    private ScanResultDto convertToDto(ScanResult scanResult) {
        ScanResultDto dto = new ScanResultDto();
        dto.setId(scanResult.getId());
        dto.setAccountId(scanResult.getAccount().getId());
        dto.setAccountName(scanResult.getAccount().getAccountName());
        dto.setProvider(scanResult.getAccount().getProvider());
        dto.setScanStartTime(scanResult.getScanStartTime());
        dto.setScanEndTime(scanResult.getScanEndTime());
        dto.setTotalResourcesScanned(scanResult.getTotalResourcesScanned());
        dto.setPassedRulesCount(scanResult.getPassedRulesCount());
        dto.setFailedRulesCount(scanResult.getFailedRulesCount());
        dto.setCriticalFindings(scanResult.getCriticalFindings());
        dto.setHighFindings(scanResult.getHighFindings());
        dto.setMediumFindings(scanResult.getMediumFindings());
        dto.setLowFindings(scanResult.getLowFindings());
        dto.setInfoFindings(scanResult.getInfoFindings());
        dto.setComplianceStandard(scanResult.getComplianceStandard());

        // Calculate compliance score
        int totalChecks = scanResult.getPassedRulesCount() + scanResult.getFailedRulesCount();
        double complianceScore = totalChecks > 0
                ? (double) scanResult.getPassedRulesCount() / totalChecks * 100
                : 0;
        dto.setComplianceScore(complianceScore);

        return dto;
    }

    private FindingDto convertToFindingDto(Finding finding) {
        FindingDto dto = new FindingDto();
        dto.setId(finding.getId());
        dto.setRuleId(finding.getRule().getRuleId());
        dto.setRuleTitle(finding.getRule().getTitle());
        dto.setResourceId(finding.getResourceId());
        dto.setResourceName(finding.getResourceName());
        dto.setResourceType(finding.getResourceType());
        dto.setSeverityLevel(finding.getSeverityLevel());
        dto.setDetails(finding.getDetails());
        dto.setCompliant(finding.isCompliant());
        dto.setRemediationSteps(finding.getRemediationSteps());
        return dto;
    }
}