package com.shahrozz.demo.DataRepositories;

import com.shahrozz.demo.ComplianceEngine.ComplianceRule;
import com.shahrozz.demo.DomainModels.CloudProvider;
import com.shahrozz.demo.DomainModels.ComplianceStandard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplianceRuleRepository extends JpaRepository<ComplianceRule, Long> {
    List<ComplianceRule> findByStandard(ComplianceStandard standard);
    List<ComplianceRule> findByCloudProvider(CloudProvider provider);
    List<ComplianceRule> findByStandardAndCloudProvider(ComplianceStandard standard, CloudProvider provider);
}