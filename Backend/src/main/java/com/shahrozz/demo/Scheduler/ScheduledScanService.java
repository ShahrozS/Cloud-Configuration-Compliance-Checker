// src/main/java/com/c4/cloudcompliancechecker/service/ScheduledScanService.java
package com.shahrozz.demo.Scheduler;

import com.shahrozz.demo.DTOs.ScanRequestDto;
import com.shahrozz.demo.DataRepositories.CloudAccountRepository;
import com.shahrozz.demo.DomainModels.CloudAccount;
import com.shahrozz.demo.DomainModels.ComplianceStandard;
import com.shahrozz.demo.Scanner.ScanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledScanService {

    private final CloudAccountRepository cloudAccountRepository;
    private final ScanService scanService;

    // Run every day at midnight
    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "*/10 * * * * ?")
    public void performDailyScans() {
        log.info("Starting scheduled daily compliance scans");

        List<CloudAccount> activeAccounts = cloudAccountRepository.findByActive(true);

        for (CloudAccount account : activeAccounts) {
            try {
                log.info("Starting scan for account: {} ({})", account.getAccountName(), account.getAccountId());

                ScanRequestDto scanRequest = new ScanRequestDto();
                System.out.println("Account id  = " + account.getId() );
                scanRequest.setAccountId(account.getId());
                scanRequest.setStandard(ComplianceStandard.CIS_BENCHMARK); // Default to CIS Benchmark
                scanService.performScan(scanRequest);

                log.info("Completed scan for account: {}", account.getAccountName());
            } catch (Exception e) {
                log.error("Error scanning account {}: {}", account.getAccountName(), e.getMessage(), e);
            }
        }

        log.info("Completed scheduled daily compliance scans");
    }

    // Run on the first day of each month at 1 AM
    @Scheduled(cron = "0 0 1 1 * ?")
    public void performMonthlyFullScans() {
        log.info("Starting scheduled monthly full compliance scans");

        List<CloudAccount> activeAccounts = cloudAccountRepository.findByActive(true);

        for (CloudAccount account : activeAccounts) {
            try {
                // Scan for CIS Benchmark
                ScanRequestDto cisScanRequest = new ScanRequestDto();
                cisScanRequest.setAccountId(account.getId());
                cisScanRequest.setStandard(ComplianceStandard.CIS_BENCHMARK);
                scanService.performScan(cisScanRequest);

                // Scan for NIST 800-53
                ScanRequestDto nistScanRequest = new ScanRequestDto();
                nistScanRequest.setAccountId(account.getId());
                nistScanRequest.setStandard(ComplianceStandard.NIST_800_53);
                scanService.performScan(nistScanRequest);

                // Scan for ISO 27001
                ScanRequestDto isoScanRequest = new ScanRequestDto();
                isoScanRequest.setAccountId(account.getId());
                isoScanRequest.setStandard(ComplianceStandard.ISO_27001);
                scanService.performScan(isoScanRequest);

                log.info("Completed full scans for account: {}", account.getAccountName());
            } catch (Exception e) {
                log.error("Error during full scan for account {}: {}", account.getAccountName(), e.getMessage(), e);
            }
        }

        log.info("Completed scheduled monthly full compliance scans");
    }
}