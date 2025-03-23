package com.shahrozz.demo.DataRepositories;

import com.shahrozz.demo.DomainModels.CloudAccount;
import com.shahrozz.demo.DomainModels.CloudProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CloudAccountRepository extends JpaRepository<CloudAccount, Long> {
    List<CloudAccount> findByProvider(CloudProvider provider);
    List<CloudAccount> findByActive(boolean active);
}
