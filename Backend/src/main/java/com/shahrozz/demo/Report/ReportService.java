package com.shahrozz.demo.Report;

import com.shahrozz.demo.DTOs.FindingDto;
import com.shahrozz.demo.DTOs.ScanResultDto;
import com.shahrozz.demo.Scanner.ScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ScanService scanService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public byte[] generateCsvReport(Long scanId) {
        ScanResultDto scanResult = scanService.getScanResultById(scanId);
        List<FindingDto> findings = scanService.getFindings(scanId, false);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (PrintWriter writer = new PrintWriter(outputStream)) {
            // Write report header
            writer.println("Cloud Configuration Compliance Report");
            writer.println("=====================================");
            writer.println("Account: " + scanResult.getAccountName());
            writer.println("Provider: " + scanResult.getProvider());
            writer.println("Standard: " + scanResult.getComplianceStandard());
            writer.println("Scan Time: " + scanResult.getScanStartTime().format(DATE_FORMATTER));
            writer.println("Compliance Score: " + String.format("%.2f%%", scanResult.getComplianceScore()));
            writer.println();

            // Write summary
            writer.println("Summary");
            writer.println("-------");
            writer.println("Total Resources Scanned: " + scanResult.getTotalResourcesScanned());
            writer.println("Passed Rules: " + scanResult.getPassedRulesCount());
            writer.println("Failed Rules: " + scanResult.getFailedRulesCount());
            writer.println();

            // Write findings by severity
            writer.println("Findings by Severity");
            writer.println("-------------------");
            writer.println("Critical: " + scanResult.getCriticalFindings());
            writer.println("High: " + scanResult.getHighFindings());
            writer.println("Medium: " + scanResult.getMediumFindings());
            writer.println("Low: " + scanResult.getLowFindings());
            writer.println("Info: " + scanResult.getInfoFindings());
            writer.println();

            // Write detailed findings
            writer.println("Detailed Findings");
            writer.println("----------------");
            writer.println("Rule ID,Rule Title,Resource ID,Resource Type,Severity,Status,Details");

            for (FindingDto finding : findings) {
                writer.print(finding.getRuleId() + ",");
                writer.print("\"" + finding.getRuleTitle() + "\",");
                writer.print(finding.getResourceId() + ",");
                writer.print(finding.getResourceType() + ",");
                writer.print(finding.getSeverityLevel() + ",");
                writer.print((finding.isCompliant() ? "PASS" : "FAIL") + ",");
                writer.println("\"" + finding.getDetails().replace("\"", "\"\"") + "\"");
            }

            // Write remediation recommendations
            writer.println();
            writer.println("Remediation Recommendations");
            writer.println("--------------------------");
            writer.println("Rule ID,Rule Title,Severity,Remediation Steps");

            // Filter for failed findings only
            List<FindingDto> failedFindings = findings.stream()
                    .filter(finding -> !finding.isCompliant())
                    .collect(Collectors.toList());

            for (FindingDto finding : failedFindings) {
                writer.print(finding.getRuleId() + ",");
                writer.print("\"" + finding.getRuleTitle() + "\",");
                writer.print(finding.getSeverityLevel() + ",");
                writer.println("\"" + finding.getRemediationSteps().replace("\"", "\"\"") + "\"");
            }
        }

        return outputStream.toByteArray();
    }

    public String generateHtmlReport(Long scanId) {
        ScanResultDto scanResult = scanService.getScanResultById(scanId);
        List<FindingDto> findings = scanService.getFindings(scanId, false);

        StringBuilder html = new StringBuilder();

        // HTML header
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("  <title>Cloud Compliance Report</title>\n");
        html.append("  <style>\n");
        html.append("    body { font-family: Arial, sans-serif; margin: 20px; }\n");
        html.append("    h1 { color: #2c3e50; }\n");
        html.append("    h2 { color: #3498db; margin-top: 20px; }\n");
        html.append("    table { border-collapse: collapse; width: 100%; margin-top: 10px; }\n");
        html.append("    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n");
        html.append("    th { background-color: #f2f2f2; }\n");
        html.append("    tr:nth-child(even) { background-color: #f9f9f9; }\n");
        html.append("    .critical { background-color: #ffcccc; }\n");
        html.append("    .high { background-color: #ffddcc; }\n");
        html.append("    .medium { background-color: #ffffcc; }\n");
        html.append("    .low { background-color: #e6ffcc; }\n");
        html.append("    .info { background-color: #e6f2ff; }\n");
        html.append("    .pass { color: green; }\n");
        html.append("    .fail { color: red; }\n");
        html.append("    .summary-box { display: inline-block; margin: 10px; padding: 15px; border-radius: 5px; min-width: 120px; text-align: center; }\n");
        html.append("    .summary-value { font-size: 24px; font-weight: bold; }\n");
        html.append("    .summary-label { font-size: 14px; }\n");
        html.append("  </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");

        // Report header
        html.append("  <h1>Cloud Configuration Compliance Report</h1>\n");
        html.append("  <p><strong>Account:</strong> ").append(scanResult.getAccountName()).append("</p>\n");
        html.append("  <p><strong>Provider:</strong> ").append(scanResult.getProvider()).append("</p>\n");
        html.append("  <p><strong>Standard:</strong> ").append(scanResult.getComplianceStandard()).append("</p>\n");
        html.append("  <p><strong>Scan Time:</strong> ").append(scanResult.getScanStartTime().format(DATE_FORMATTER)).append("</p>\n");

        // Summary boxes
        html.append("  <h2>Summary</h2>\n");
        html.append("  <div>\n");
        html.append("    <div class='summary-box' style='background-color: #e6f2ff;'>\n");
        html.append("      <div class='summary-value'>").append(String.format("%.1f%%", scanResult.getComplianceScore())).append("</div>\n");
        html.append("      <div class='summary-label'>Compliance Score</div>\n");
        html.append("    </div>\n");
        html.append("    <div class='summary-box' style='background-color: #e6ffcc;'>\n");
        html.append("      <div class='summary-value'>").append(scanResult.getTotalResourcesScanned()).append("</div>\n");
        html.append("      <div class='summary-label'>Resources Scanned</div>\n");
        html.append("    </div>\n");
        html.append("    <div class='summary-box' style='background-color: #ccffcc;'>\n");
        html.append("      <div class='summary-value'>").append(scanResult.getPassedRulesCount()).append("</div>\n");
        html.append("      <div class='summary-label'>Passed Rules</div>\n");
        html.append("    </div>\n");
        html.append("    <div class='summary-box' style='background-color: #ffcccc;'>\n");
        html.append("      <div class='summary-value'>").append(scanResult.getFailedRulesCount()).append("</div>\n");
        html.append("      <div class='summary-label'>Failed Rules</div>\n");
        html.append("    </div>\n");
        html.append("  </div>\n");

        // Findings by severity
        html.append("  <h2>Findings by Severity</h2>\n");
        html.append("  <div>\n");
        html.append("    <div class='summary-box critical'>\n");
        html.append("      <div class='summary-value'>").append(scanResult.getCriticalFindings()).append("</div>\n");
        html.append("      <div class='summary-label'>Critical</div>\n");
        html.append("    </div>\n");
        html.append("    <div class='summary-box high'>\n");
        html.append("      <div class='summary-value'>").append(scanResult.getHighFindings()).append("</div>\n");
        html.append("      <div class='summary-label'>High</div>\n");
        html.append("    </div>\n");
        html.append("    <div class='summary-box medium'>\n");
        html.append("      <div class='summary-value'>").append(scanResult.getMediumFindings()).append("</div>\n");
        html.append("      <div class='summary-label'>Medium</div>\n");
        html.append("    </div>\n");
        html.append("    <div class='summary-box low'>\n");
        html.append("      <div class='summary-value'>").append(scanResult.getLowFindings()).append("</div>\n");
        html.append("      <div class='summary-label'>Low</div>\n");
        html.append("    </div>\n");
        html.append("    <div class='summary-box info'>\n");
        html.append("      <div class='summary-value'>").append(scanResult.getInfoFindings()).append("</div>\n");
        html.append("      <div class='summary-label'>Info</div>\n");
        html.append("    </div>\n");
        html.append("  </div>\n");

        // Detailed findings
        html.append("  <h2>Detailed Findings</h2>\n");
        html.append("  <table>\n");
        html.append("    <tr>\n");
        html.append("      <th>Rule ID</th>\n");
        html.append("      <th>Rule Title</th>\n");
        html.append("      <th>Resource ID</th>\n");
        html.append("      <th>Resource Type</th>\n");
        html.append("      <th>Severity</th>\n");
        html.append("      <th>Status</th>\n");
        html.append("      <th>Details</th>\n");
        html.append("    </tr>\n");

        for (FindingDto finding : findings) {
            String severityClass = finding.getSeverityLevel().toString().toLowerCase();
            String statusClass = finding.isCompliant() ? "pass" : "fail";
            String status = finding.isCompliant() ? "PASS" : "FAIL";

            html.append("    <tr class='").append(severityClass).append("'>\n");
            html.append("      <td>").append(finding.getRuleId()).append("</td>\n");
            html.append("      <td>").append(finding.getRuleTitle()).append("</td>\n");
            html.append("      <td>").append(finding.getResourceId()).append("</td>\n");
            html.append("      <td>").append(finding.getResourceType()).append("</td>\n");
            html.append("      <td>").append(finding.getSeverityLevel()).append("</td>\n");
            html.append("      <td class='").append(statusClass).append("'>").append(status).append("</td>\n");
            html.append("      <td>").append(finding.getDetails()).append("</td>\n");
            html.append("    </tr>\n");
        }

        html.append("  </table>\n");

        // Remediation recommendations
        html.append("  <h2>Remediation Recommendations</h2>\n");
        html.append("  <table>\n");
        html.append("    <tr>\n");
        html.append("      <th>Rule ID</th>\n");
        html.append("      <th>Rule Title</th>\n");
        html.append("      <th>Severity</th>\n");
        html.append("      <th>Remediation Steps</th>\n");
        html.append("    </tr>\n");

        // Filter for failed findings only
        List<FindingDto> failedFindings = findings.stream()
                .filter(finding -> !finding.isCompliant())
                .collect(Collectors.toList());

        for (FindingDto finding : failedFindings) {
            String severityClass = finding.getSeverityLevel().toString().toLowerCase();

            html.append("    <tr class='").append(severityClass).append("'>\n");
            html.append("      <td>").append(finding.getRuleId()).append("</td>\n");
            html.append("      <td>").append(finding.getRuleTitle()).append("</td>\n");
            html.append("      <td>").append(finding.getSeverityLevel()).append("</td>\n");
            html.append("      <td>").append(finding.getRemediationSteps()).append("</td>\n");
            html.append("    </tr>\n");
        }

        html.append("  </table>\n");

        // HTML footer
        html.append("</body>\n");
        html.append("</html>");

        return html.toString();
    }
}
