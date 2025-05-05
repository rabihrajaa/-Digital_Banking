package org.sid.digitalbanking_backend;

import org.sid.digitalbanking_backend.entities.Customer;
import org.sid.digitalbanking_backend.repositories.AccountOperationRepository;
import org.sid.digitalbanking_backend.repositories.BankAccountRepository;
import org.sid.digitalbanking_backend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingBackendApplication.class, args);
    }

    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Rajaa","Asma","Marwa").forEach(name -> {
                    Customer customer = new Customer();
                    customer.setName(name);
                    customer.setEmail(name+"@gmail.com");
                    customerRepository.save(customer);
            });
        };
    }

}
