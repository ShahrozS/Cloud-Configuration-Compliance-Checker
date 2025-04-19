package com.shahrozz.demo.Services;

import com.shahrozz.demo.DomainModels.CloudAccount;
import com.shahrozz.demo.DomainModels.CloudProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CloudProviderFactory {
    private final AwsComplianceService awsComplianceService;
    private final AzureComplianceService azureComplianceService;
    private final GcpComplianceService gcpComplianceService;

    public CloudComplianceService getProviderService(CloudAccount account) {
        if (account.getProvider() == CloudProvider.AWS) {
            return awsComplianceService;
        } else if (account.getProvider() == CloudProvider.AZURE) {
            return azureComplianceService;
        } else if (account.getProvider() == CloudProvider.GCP) {
            return gcpComplianceService;
        }
        throw new IllegalArgumentException("Unsupported cloud provider: " + account.getProvider());
    }
}
