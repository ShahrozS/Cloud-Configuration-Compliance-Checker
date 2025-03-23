package com.shahrozz.demo.ComplianceEngine;

import com.shahrozz.demo.AWS.AwsScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplianceEngine {

    @Autowired
    private AwsScannerService awsScanner;

    @Autowired
    private RuleLoader ruleLoader;

    public ComplianceResult checkCompliance(String framework) {
        List<ComplianceRule> rules = ruleLoader.loadRules(framework);
        ComplianceResult result = new ComplianceResult();

        for(ComplianceRule rule : rules) {
            switch(rule.getService()) {
                case "ec2":
                    result.addCheck(rule, awsScanner.checkEC2Compliance(rule));
                    break;
                case "s3":
                    result.addCheck(rule, awsScanner.checkS3Compliance(rule));
                    break;
            }
        }
        return result;
    }
}