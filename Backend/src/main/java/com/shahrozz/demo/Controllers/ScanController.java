package com.shahrozz.demo.Controllers;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.shahrozz.demo.Models.ScanResult;
import com.shahrozz.demo.Repository.ScanResultRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scans")
public class ScanController {
    private final ScanResultRepository scanResultRepository;

    public ScanController(ScanResultRepository scanResultRepository) {
        this.scanResultRepository = scanResultRepository;
    }

    @PostMapping("/iac")
    public ResponseEntity<String> scanIaC(@RequestBody String terraformCode) {
        // Implement scanning logic
    }

    @GetMapping("/aws")
    public ResponseEntity<List<SecurityGroup>> scanAWS() {
        // Implement AWS scanning
    }
}

