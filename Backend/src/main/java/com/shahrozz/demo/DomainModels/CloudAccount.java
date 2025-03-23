package com.shahrozz.demo.DomainModels;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class CloudAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountName;
    private String accountId;

    @Enumerated(EnumType.STRING)
    private CloudProvider provider;

    private String accessKeyId;

    @Column(length = 1000)
    private String secretAccessKey;

    private String region;
    private boolean active = true;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<ScanResult> scanResults = new HashSet<>();
}