package com.shahrozz.demo.Schedulers;

import com.shahrozz.demo.Service.AwsScannerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScanScheduler {
    private final AwsScannerService awsScannerService;

    public ScanScheduler(AwsScannerService awsScannerService) {
        this.awsScannerService = awsScannerService;
    }

    @Scheduled(cron = "0 0 * * * ?") // Runs every hour
    public void runCloudScans() {
        awsScannerService.checkS3Buckets();
        System.out.println("AWS Scan Completed!");
    }
}
