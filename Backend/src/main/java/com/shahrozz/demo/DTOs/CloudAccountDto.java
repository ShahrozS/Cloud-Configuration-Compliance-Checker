package com.shahrozz.demo.DTOs;

import com.shahrozz.demo.DomainModels.CloudProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CloudAccountDto {

    @NotBlank(message = "Account name is required")
    private String accountName;

    @NotBlank(message = "Account ID is required")
    private String accountId;

    @NotNull(message = "Cloud provider is required")
    private CloudProvider provider;

    @NotBlank(message = "Access key ID is required")
    private String accessKeyId;

    @NotBlank(message = "Secret access key is required")
    private String secretAccessKey;

    private String region;
    private boolean active = true;
}

