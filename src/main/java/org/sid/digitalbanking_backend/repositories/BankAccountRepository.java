package org.sid.digitalbanking_backend.repositories;

import org.sid.digitalbanking_backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
