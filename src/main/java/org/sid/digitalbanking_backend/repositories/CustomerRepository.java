package org.sid.digitalbanking_backend.repositories;

import org.sid.digitalbanking_backend.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
