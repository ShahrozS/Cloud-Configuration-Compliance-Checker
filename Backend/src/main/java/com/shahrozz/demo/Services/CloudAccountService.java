package com.shahrozz.demo.Services;

import com.shahrozz.demo.DTOs.CloudAccountDto;
import com.shahrozz.demo.DataRepositories.CloudAccountRepository;
import com.shahrozz.demo.DomainModels.CloudAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CloudAccountService {
    private final CloudAccountRepository cloudAccountRepository;

    public List<CloudAccountDto> getAllAccounts() {
        return cloudAccountRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CloudAccountDto getAccountById(Long id) {
        CloudAccount account = cloudAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        return convertToDto(account);
    }

    public CloudAccountDto createAccount(CloudAccountDto accountDto) {
        CloudAccount account = convertToEntity(accountDto);
        CloudAccount savedAccount = cloudAccountRepository.save(account);
        return convertToDto(savedAccount);
    }

    public CloudAccountDto updateAccount(Long id, CloudAccountDto accountDto) {
        CloudAccount existingAccount = cloudAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        existingAccount.setAccountName(accountDto.getAccountName());
        existingAccount.setAccountId(accountDto.getAccountId());
        existingAccount.setProvider(accountDto.getProvider());
        existingAccount.setAccessKeyId(accountDto.getAccessKeyId());
        existingAccount.setSecretAccessKey(accountDto.getSecretAccessKey());
        existingAccount.setRegion(accountDto.getRegion());
        existingAccount.setActive(accountDto.isActive());

        CloudAccount updatedAccount = cloudAccountRepository.save(existingAccount);
        return convertToDto(updatedAccount);
    }

    public void deleteAccount(Long id) {
        cloudAccountRepository.deleteById(id);
    }

    private CloudAccountDto convertToDto(CloudAccount account) {
        CloudAccountDto dto = new CloudAccountDto();
        dto.setId(account.getId());
        dto.setAccountName(account.getAccountName());
        dto.setAccountId(account.getAccountId());
        dto.setProvider(account.getProvider());
        dto.setAccessKeyId(account.getAccessKeyId());
        dto.setSecretAccessKey("*****"); // Don't expose the actual secret key
        dto.setRegion(account.getRegion());
        dto.setActive(account.isActive());
        return dto;
    }

    private CloudAccount convertToEntity(CloudAccountDto dto) {
        CloudAccount account = new CloudAccount();
        account.setId(dto.getId());
        account.setAccountName(dto.getAccountName());
        account.setAccountId(dto.getAccountId());
        account.setProvider(dto.getProvider());
        account.setAccessKeyId(dto.getAccessKeyId());
        account.setSecretAccessKey(dto.getSecretAccessKey());
        account.setRegion(dto.getRegion());
        account.setActive(dto.isActive());
        return account;
    }
}

