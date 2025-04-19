package com.shahrozz.demo.Services;

import com.shahrozz.demo.DomainModels.CloudAccount;
import com.shahrozz.demo.DomainModels.ComplianceRule;
import com.shahrozz.demo.DomainModels.Finding;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GcpComplianceService implements CloudComplianceService {
    @Override
    public void authenticate(CloudAccount account) {
        // In a real implementation, this would use GCP SDK to authenticate
        // For example:
        /*
        GoogleCredentials credentials = GoogleCredentials.fromStream(
            new FileInputStream(account.getSecretAccessKey())
        );
        */

        System.out.println("Authenticated with GCP account: " + account.getAccountId());
    }

    @Override
    public Map<String, Object> scanResource(String resourceId, String resourceType, ComplianceRule rule) {
        // In a real implementation, this would use GCP SDK to scan the resource
        // For this example, we'll return a mock result
        Map<String, Object> result = new HashMap<>();
        result.put("resourceId", resourceId);
        result.put("resourceType", resourceType);
        result.put("compliant", new Random().nextBoolean());
        result.put("details", "Mock scan result for GCP resource " + resourceId);
        return result;
    }

    @Override
    public List<Map<String, Object>> listResources(String resourceType) {
        List<Map<String, Object>> resources = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Map<String, Object> resource = new HashMap<>();
            resource.put("resourceId", "gcp-" + resourceType + "-" + i);
            resource.put("resourceName", "Mock " + resourceType + " " + i);
            resource.put("resourceType", resourceType);
            resources.add(resource);
        }
        return resources;
    }

    @Override
    public List<Finding> evaluateCompliance(CloudAccount account, List<ComplianceRule> rules) {
        authenticate(account);

        List<Finding> findings = new ArrayList<>();
        Random random = new Random();

        for (ComplianceRule rule : rules) {
            String resourceType = rule.getResourceType();
            List<Map<String, Object>> resources = listResources(resourceType);

            for (Map<String, Object> resource : resources) {
                Map<String, Object> scanResult = scanResource(
                        (String) resource.get("resourceId"),
                        resourceType,
                        rule
                );

                Finding finding = new Finding();
                finding.setResourceId((String) resource.get("resourceId"));
                finding.setResourceName((String) resource.get("resourceName"));
                finding.setResourceType(resourceType);
                finding.setRule(rule);
                finding.setSeverityLevel(rule.getSeverityLevel());
                finding.setDetails((String) scanResult.get("details"));
                finding.setCompliant((Boolean) scanResult.get("compliant"));

                if (!finding.isCompliant()) {
                    finding.setRemediationSteps(rule.getRemediationSteps());
                }

                findings.add(finding);
            }
        }

        return findings;
    }

    @Override
    public int getTotalResourceCount(CloudAccount account) {
        // This would normally count resources across all types
        // For this example, we'll return a mock count
        return 20;
    }
}