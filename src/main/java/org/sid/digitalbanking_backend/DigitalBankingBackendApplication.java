package org.sid.digitalbanking_backend;

import org.sid.digitalbanking_backend.entities.AccountOperetion;
import org.sid.digitalbanking_backend.entities.CurrentAccount;
import org.sid.digitalbanking_backend.entities.Customer;
import org.sid.digitalbanking_backend.entities.SavingAccount;
import org.sid.digitalbanking_backend.enums.AccountStatus;
import org.sid.digitalbanking_backend.enums.OperationType;
import org.sid.digitalbanking_backend.repositories.AccountOperationRepository;
import org.sid.digitalbanking_backend.repositories.BankAccountRepository;
import org.sid.digitalbanking_backend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingBackendApplication.class, args);
    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository
                             ) {
        return args -> {
            Stream.of("Rajaa","Asma","Marwa").forEach(name -> {
                    Customer customer = new Customer();
                    customer.setName(name);
                    customer.setEmail(name+"@gmail.com");
                    customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCustomer(cust);
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);


                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCustomer(cust);
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });

            bankAccountRepository.findAll().forEach(acc->{

                for (int i=0;i<10;i++){
                    AccountOperetion accountOperation = new AccountOperetion();
                    accountOperation.setOpeartionDate (new Date());
                    accountOperation.setAmount (Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount (acc);
                    accountOperationRepository.save(accountOperation);
                }


            });

        };
    }

}
