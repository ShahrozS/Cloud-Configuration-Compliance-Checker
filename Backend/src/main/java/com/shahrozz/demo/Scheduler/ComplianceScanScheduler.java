package com.shahrozz.demo.Scheduler;
import com.shahrozz.demo.DTOs.ScanRequestDto;
import com.shahrozz.demo.DataRepositories.CloudAccountRepository;
import com.shahrozz.demo.DomainModels.CloudAccount;
import com.shahrozz.demo.DomainModels.ComplianceStandard;
import com.shahrozz.demo.Scanner.ScanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ComplianceScanScheduler {
    private final CloudAccountRepository cloudAccountRepository;
    private final ScanService scanService;

    // Run daily at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void runDailyComplianceScans() {
        log.info("Starting scheduled compliance scans");

        List<CloudAccount> activeAccounts = cloudAccountRepository.findByActive(true);

        for (CloudAccount account : activeAccounts) {
            try {
                ScanRequestDto scanRequest = new ScanRequestDto();
                scanRequest.setAccountId(account.getId());
                scanRequest.setStandard(ComplianceStandard.CIS_BENCHMARK);
                scanRequest.setIncludePassedRules(true);

                scanService.performScan(scanRequest);
                log.info("Completed scheduled scan for account: {}", account.getAccountName());
            } catch (Exception e) {
                log.error("Failed to run scheduled scan for account: {}", account.getAccountName(), e);
            }
        }

        log.info("Completed all scheduled compliance scans");
    }
}