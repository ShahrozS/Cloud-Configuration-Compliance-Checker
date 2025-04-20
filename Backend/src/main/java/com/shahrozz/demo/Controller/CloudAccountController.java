package com.shahrozz.demo.Controller;

import com.shahrozz.demo.DTOs.CloudAccountDto;
import com.shahrozz.demo.Services.CloudAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class CloudAccountController {
    private final CloudAccountService cloudAccountService;

    @GetMapping("/allAccounts")
    public ResponseEntity<List<CloudAccountDto>> getAllAccounts() {
        System.out.println("Getting all the accounts");
        return ResponseEntity.ok(cloudAccountService.getAllAccounts());
    }

    @GetMapping("/getAccount/{id}")
    public ResponseEntity<CloudAccountDto> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(cloudAccountService.getAccountById(id));
    }

    @PostMapping("/addAccount")
    public ResponseEntity<CloudAccountDto> createAccount(@Valid @RequestBody CloudAccountDto accountDto) {
        return new ResponseEntity<>(cloudAccountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @PutMapping("/updateAccount/{id}")
    public ResponseEntity<CloudAccountDto> updateAccount(
            @PathVariable Long id,
            @Valid @RequestBody CloudAccountDto accountDto) {
        return ResponseEntity.ok(cloudAccountService.updateAccount(id, accountDto));
    }

    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        cloudAccountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}