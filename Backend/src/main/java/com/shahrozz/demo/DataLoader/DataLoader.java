package com.shahrozz.demo.DataLoader;

import com.shahrozz.demo.DataRepositories.ComplianceRuleRepository;
import com.shahrozz.demo.DomainModels.CloudProvider;
import com.shahrozz.demo.DomainModels.ComplianceRule;
import com.shahrozz.demo.DomainModels.ComplianceStandard;
import com.shahrozz.demo.DomainModels.SeverityLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final ComplianceRuleRepository complianceRuleRepository;

    @Override
    public void run(String... args) {
        if (complianceRuleRepository.count() == 0) {
            loadInitialRules();
        }
    }

    private void loadInitialRules() {
        List<ComplianceRule> rules = new ArrayList<>();

        // AWS CIS Benchmark Rules
        rules.add(createRule(
                "AWS-CIS-1.1",
                "Avoid using the root account",
                "The root account has unrestricted access to all resources in the AWS account. It is highly recommended to avoid using the root account for everyday tasks.",
                SeverityLevel.CRITICAL,
                ComplianceStandard.CIS_BENCHMARK,
                CloudProvider.AWS,
                "Create individual IAM users for administrative tasks and disable root account access.",
                "IAMUser"
        ));

        rules.add(createRule(
                "AWS-CIS-1.2",
                "Ensure MFA is enabled for all IAM users that have a console password",
                "Multi-Factor Authentication (MFA) adds an extra layer of protection on top of a username and password.",
                SeverityLevel.HIGH,
                ComplianceStandard.CIS_BENCHMARK,
                CloudProvider.AWS,
                "Enable MFA for all IAM users with console access in the Identity and Access Management (IAM) console.",
                "IAMUser"
        ));

        rules.add(createRule(
                "AWS-CIS-2.1",
                "Ensure CloudTrail is enabled in all regions",
                "AWS CloudTrail is a service that enables governance, compliance, and operational and risk auditing of your AWS account.",
                SeverityLevel.HIGH,
                ComplianceStandard.CIS_BENCHMARK,
                CloudProvider.AWS,
                "Enable CloudTrail in all AWS regions from the CloudTrail console.",
                "CloudTrail"
        ));

        rules.add(createRule(
                "AWS-CIS-2.2",
                "Ensure CloudTrail log file validation is enabled",
                "CloudTrail log file validation creates a digitally signed digest file containing a hash of each log that CloudTrail writes to S3.",
                SeverityLevel.MEDIUM,
                ComplianceStandard.CIS_BENCHMARK,
                CloudProvider.AWS,
                "Enable log file validation for all CloudTrail trails.",
                "CloudTrail"
        ));

        rules.add(createRule(
                "AWS-CIS-4.1",
                "Ensure no security groups allow ingress from 0.0.0.0/0 to port 22",
                "Security groups provide stateful filtering of ingress and egress network traffic to AWS resources.",
                SeverityLevel.HIGH,
                ComplianceStandard.CIS_BENCHMARK,
                CloudProvider.AWS,
                "Remove any inbound rules in security groups that permit unrestricted access to port 22.",
                "SecurityGroup"
        ));

        // Azure CIS Benchmark Rules
        rules.add(createRule(
                "AZURE-CIS-1.1",
                "Ensure that multi-factor authentication is enabled for all privileged users",
                "Multi-factor authentication requires an individual to present a minimum of two separate forms of authentication.",
                SeverityLevel.CRITICAL,
                ComplianceStandard.CIS_BENCHMARK,
                CloudProvider.AZURE,
                "Enable MFA for all privileged accounts through Azure Active Directory.",
                "AzureADUser"
        ));

        rules.add(createRule(
                "AZURE-CIS-1.2",
                "Ensure that there are no guest users",
                "Azure AD is extended to include Azure AD B2B collaboration, allowing you to invite people from outside your organization to be guest users.",
                SeverityLevel.MEDIUM,
                ComplianceStandard.CIS_BENCHMARK,
                CloudProvider.AZURE,
                "Review guest users in Azure AD and remove any that are not required.",
                "AzureADUser"
        ));

        rules.add(createRule(
                "AZURE-CIS-2.1",
                "Ensure that Activity Log Alert exists for Create, Update and Delete Network Security Group",
                "Monitoring for Create, Update, and Delete Network Security Group events gives insight into network access changes.",
                SeverityLevel.HIGH,
                ComplianceStandard.CIS_BENCHMARK,
                CloudProvider.AZURE,
                "Create Activity Log Alerts for Network Security Group create, update and delete events.",
                "ActivityLog"
        ));

        // GCP CIS Benchmark Rules
        rules.add(createRule(
                "GCP-CIS-1.1",
                "Ensure that corporate login credentials are used",
                "Use corporate login credentials instead of personal accounts, such as Gmail accounts.",
                SeverityLevel.HIGH,
                ComplianceStandard.CIS_BENCHMARK,
                CloudProvider.GCP,
                "Enforce corporate login credentials via organizational policies.",
                "IAMUser"
        ));

        rules.add(createRule(
                "GCP-CIS-1.2",
                "Ensure that multi-factor authentication is enabled for all non-service accounts",
                "Multi-factor authentication requires more than one mechanism to authenticate a user.",
                SeverityLevel.CRITICAL,
                ComplianceStandard.CIS_BENCHMARK,
                CloudProvider.GCP,
                "Enable MFA for all human user accounts.",
                "IAMUser"
        ));

        rules.add(createRule(
                "GCP-CIS-2.1",
                "Ensure that Cloud Audit Logging is configured properly",
                "Cloud Audit Logging maintains audit records for Google Cloud Platform services.",
                SeverityLevel.HIGH,
                ComplianceStandard.CIS_BENCHMARK,
                CloudProvider.GCP,
                "Enable audit logging for all services and all users.",
                "CloudAudit"
        ));

        // NIST Rules
        rules.add(createRule(
                "AWS-NIST-1.1",
                "Implement least privilege access control",
                "The principle of least privilege requires that a user is given no more privilege than necessary to perform a job.",
                SeverityLevel.HIGH,
                ComplianceStandard.NIST_800_53,
                CloudProvider.AWS,
                "Review and restrict IAM policies to provide minimal necessary permissions.",
                "IAMPolicy"
        ));

        rules.add(createRule(
                "AZURE-NIST-1.1",
                "Implement least privilege access control",
                "The principle of least privilege requires that a user is given no more privilege than necessary to perform a job.",
                SeverityLevel.HIGH,
                ComplianceStandard.NIST_800_53,
                CloudProvider.AZURE,
                "Review and restrict role assignments to provide minimal necessary permissions.",
                "AzureRBAC"
        ));

        rules.add(createRule(
                "GCP-NIST-1.1",
                "Implement least privilege access control",
                "The principle of least privilege requires that a user is given no more privilege than necessary to perform a job.",
                SeverityLevel.HIGH,
                ComplianceStandard.NIST_800_53,
                CloudProvider.GCP,
                "Review and restrict IAM roles to provide minimal necessary permissions.",
                "IAMPolicy"
        ));

        // ISO 27001 Rules
        rules.add(createRule(
                "AWS-ISO-1.1",
                "Ensure encryption of data at rest",
                "All sensitive data stored in cloud storage should be encrypted to protect confidentiality.",
                SeverityLevel.HIGH,
                ComplianceStandard.ISO_27001,
                CloudProvider.AWS,
                "Enable default encryption for S3 buckets and EBS volumes.",
                "S3Bucket"
        ));

        rules.add(createRule(
                "AZURE-ISO-1.1",
                "Ensure encryption of data at rest",
                "All sensitive data stored in cloud storage should be encrypted to protect confidentiality.",
                SeverityLevel.HIGH,
                ComplianceStandard.ISO_27001,
                CloudProvider.AZURE,
                "Enable Storage Service Encryption for all storage accounts.",
                "StorageAccount"
        ));

        rules.add(createRule(
                "GCP-ISO-1.1",
                "Ensure encryption of data at rest",
                "All sensitive data stored in cloud storage should be encrypted to protect confidentiality.",
                SeverityLevel.HIGH,
                ComplianceStandard.ISO_27001,
                CloudProvider.GCP,
                "Enable default encryption for Cloud Storage buckets.",
                "StorageBucket"
        ));

        complianceRuleRepository.saveAll(rules);
    }

    private ComplianceRule createRule(
            String ruleId,
            String title,
            String description,
            SeverityLevel severityLevel,
            ComplianceStandard standard,
            CloudProvider cloudProvider,
            String remediationSteps,
            String resourceType) {

        ComplianceRule rule = new ComplianceRule();
        rule.setRuleId(ruleId);
        rule.setTitle(title);
        rule.setDescription(description);
        rule.setSeverityLevel(severityLevel);
        rule.setStandard(standard);
        rule.setCloudProvider(cloudProvider);
        rule.setRemediationSteps(remediationSteps);
        rule.setResourceType(resourceType);
        return rule;
    }
}