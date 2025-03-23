package com.shahrozz.demo.Controller;

import com.shahrozz.demo.DTOs.ComplianceRuleDto;
import com.shahrozz.demo.DomainModels.CloudProvider;
import com.shahrozz.demo.DomainModels.ComplianceStandard;
import com.shahrozz.demo.Services.ComplianceRuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@RequiredArgsConstructor
public class ComplianceRuleController {
    private final ComplianceRuleService complianceRuleService;

    @GetMapping
    public ResponseEntity<List<ComplianceRuleDto>> getAllRules() {
        return ResponseEntity.ok(complianceRuleService.getAllRules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplianceRuleDto> getRuleById(@PathVariable Long id) {
        return ResponseEntity.ok(complianceRuleService.getRuleById(id));
    }

    @GetMapping("/standard/{standard}")
    public ResponseEntity<List<ComplianceRuleDto>> getRulesByStandard(
            @PathVariable ComplianceStandard standard) {
        return ResponseEntity.ok(complianceRuleService.getRulesByStandard(standard));
    }

    @GetMapping("/provider/{provider}")
    public ResponseEntity<List<ComplianceRuleDto>> getRulesByProvider(
            @PathVariable CloudProvider provider) {
        return ResponseEntity.ok(complianceRuleService.getRulesByProvider(provider));
    }

    @GetMapping("/standard/{standard}/provider/{provider}")
    public ResponseEntity<List<ComplianceRuleDto>> getRulesByStandardAndProvider(
            @PathVariable ComplianceStandard standard,
            @PathVariable CloudProvider provider) {
        return ResponseEntity.ok(complianceRuleService.getRulesByStandardAndProvider(standard, provider));
    }

    @PostMapping
    public ResponseEntity<ComplianceRuleDto> createRule(@Valid @RequestBody ComplianceRuleDto ruleDto) {
        return new ResponseEntity<>(complianceRuleService.createRule(ruleDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComplianceRuleDto> updateRule(
            @PathVariable Long id,
            @Valid @RequestBody ComplianceRuleDto ruleDto) {
        return ResponseEntity.ok(complianceRuleService.updateRule(id, ruleDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        complianceRuleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
}