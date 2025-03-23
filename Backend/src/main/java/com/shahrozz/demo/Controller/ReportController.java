package com.shahrozz.demo.Controller;

import com.shahrozz.demo.Report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/{scanId}/csv")
    public ResponseEntity<byte[]> generateCsvReport(@PathVariable Long scanId) {
        byte[] report = reportService.generateCsvReport(scanId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "compliance_report_" + scanId + ".csv");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return ResponseEntity.ok()
                .headers(headers)
                .body(report);
    }

    @GetMapping("/{scanId}/html")
    public ResponseEntity<String> generateHtmlReport(@PathVariable Long scanId) {
        String report = reportService.generateHtmlReport(scanId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/html"));

        return ResponseEntity.ok()
                .headers(headers)
                .body(report);
    }
}