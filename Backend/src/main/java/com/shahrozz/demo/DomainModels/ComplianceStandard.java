package com.shahrozz.demo.DomainModels;

public enum ComplianceStandard {
    CIS_BENCHMARK("CIS Benchmark"),
    NIST_800_53("NIST 800-53"),
    ISO_27001("ISO 27001"),
    HIPAA("HIPAA"),
    PCI_DSS("PCI DSS");

    private final String displayName;

    ComplianceStandard(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
