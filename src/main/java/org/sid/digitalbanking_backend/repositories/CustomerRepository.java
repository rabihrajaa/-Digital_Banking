package org.sid.digitalbanking_backend.repositories;

import org.sid.digitalbanking_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
