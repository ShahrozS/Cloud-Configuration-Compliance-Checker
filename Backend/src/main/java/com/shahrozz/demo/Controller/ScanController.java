package com.shahrozz.demo.Controller;

import com.shahrozz.demo.DTOs.FindingDto;
import com.shahrozz.demo.DTOs.ScanRequestDto;
import com.shahrozz.demo.DTOs.ScanResultDto;
import com.shahrozz.demo.Scanner.ScanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scans")
@RequiredArgsConstructor
public class ScanController {
    private final ScanService scanService;

    @PostMapping
    public ResponseEntity<ScanResultDto> performScan(@Valid @RequestBody ScanRequestDto scanRequestDto) {
        return new ResponseEntity<>(scanService.performScan(scanRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScanResultDto>> getAllScanResults() {
        return ResponseEntity.ok(scanService.getAllScanResults());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScanResultDto> getScanResultById(@PathVariable Long id) {
        return ResponseEntity.ok(scanService.getScanResultById(id));
    }

    @GetMapping("/{id}/findings")
    public ResponseEntity<List<FindingDto>> getFindings(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") boolean onlyFailed) {
        return ResponseEntity.ok(scanService.getFindings(id, onlyFailed));
    }
}