package com.shahrozz.demo.Services;


import com.shahrozz.demo.DomainModels.CloudAccount;
import com.shahrozz.demo.DomainModels.ComplianceRule;
import com.shahrozz.demo.DomainModels.Finding;

import java.util.List;
import java.util.Map;

public interface CloudComplianceService {
    void authenticate(CloudAccount account);


    Map<String, Object> scanResource(String resourceId, String resourceType, ComplianceRule rule);




    List<Map<String, Object>> listResources(String resourceType);

    List<Finding> evaluateCompliance(CloudAccount account, List<ComplianceRule> rules);

    int getTotalResourceCount(CloudAccount account);
}

