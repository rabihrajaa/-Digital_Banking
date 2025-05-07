package org.sid.digitalbanking_backend.repositories;

import org.sid.digitalbanking_backend.entities.AccountOperetion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperetion, Long> {
    List<AccountOperetion> findByBankAccountId(String accountId);
    Page<AccountOperetion> findByBankAccountIdOrderByOperationDateDesc(String accountId, Pageable pageable);
}
