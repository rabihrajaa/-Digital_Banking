package org.sid.digitalbanking_backend.repositories;

import org.sid.digitalbanking_backend.entities.AccountOperetion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperetion, Long> {

}
