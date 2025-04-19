package com.shahrozz.demo.Services;

import com.shahrozz.demo.DTOs.ComplianceRuleDto;
import com.shahrozz.demo.DataRepositories.ComplianceRuleRepository;
import com.shahrozz.demo.DomainModels.CloudProvider;
import com.shahrozz.demo.DomainModels.ComplianceRule;
import com.shahrozz.demo.DomainModels.ComplianceStandard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComplianceRuleService {
    private final ComplianceRuleRepository complianceRuleRepository;

    public List<ComplianceRuleDto> getAllRules() {
        return complianceRuleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ComplianceRuleDto> getRulesByStandard(ComplianceStandard standard) {
        return complianceRuleRepository.findByStandard(standard).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ComplianceRuleDto> getRulesByProvider(CloudProvider provider) {
        return complianceRuleRepository.findByCloudProvider(provider).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ComplianceRuleDto> getRulesByStandardAndProvider(ComplianceStandard standard, CloudProvider provider) {
        return complianceRuleRepository.findByStandardAndCloudProvider(standard, provider).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ComplianceRuleDto getRuleById(Long id) {
        ComplianceRule rule = complianceRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found with id: " + id));
        return convertToDto(rule);
    }

    public ComplianceRuleDto createRule(ComplianceRuleDto ruleDto) {
        ComplianceRule rule = convertToEntity(ruleDto);
        ComplianceRule savedRule = complianceRuleRepository.save(rule);
        return convertToDto(savedRule);
    }

    public ComplianceRuleDto updateRule(Long id, ComplianceRuleDto ruleDto) {
        ComplianceRule existingRule = complianceRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found with id: " + id));

        existingRule.setId(ruleDto.getId());
        existingRule.setTitle(ruleDto.getTitle());
        existingRule.setDescription(ruleDto.getDescription());
        existingRule.setSeverityLevel(ruleDto.getSeverityLevel());
        existingRule.setCloudProvider(ruleDto.getCloudProvider());

        ComplianceRule updatedRule = complianceRuleRepository.save(existingRule);
        return convertToDto(updatedRule);
    }

    public void deleteRule(Long id) {
        complianceRuleRepository.deleteById(id);
    }

    private ComplianceRuleDto convertToDto(ComplianceRule rule) {
        ComplianceRuleDto dto = new ComplianceRuleDto();
        dto.setId(rule.getId());
        dto.setRuleId(rule.getRuleId());
        dto.setTitle(rule.getTitle());
        dto.setDescription(rule.getDescription());
        dto.setSeverityLevel(rule.getSeverityLevel());
        dto.setStandard(rule.getStandard());
        dto.setCloudProvider(rule.getCloudProvider());
        dto.setRemediationSteps(rule.getRemediationSteps());
        dto.setResourceType(rule.getResourceType());
        return dto;
    }

    private ComplianceRule convertToEntity(ComplianceRuleDto dto) {
        ComplianceRule rule = new ComplianceRule();
        rule.setId(dto.getId());
        rule.setRuleId(dto.getRuleId());
        rule.setTitle(dto.getTitle());
        rule.setDescription(dto.getDescription());
        rule.setSeverityLevel(dto.getSeverityLevel());
        rule.setStandard(dto.getStandard());
        rule.setCloudProvider(dto.getCloudProvider());
        rule.setRemediationSteps(dto.getRemediationSteps());
        rule.setResourceType(dto.getResourceType());
        return rule;
    }
}
