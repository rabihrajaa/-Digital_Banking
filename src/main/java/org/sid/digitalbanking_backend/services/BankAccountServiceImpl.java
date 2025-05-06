package org.sid.digitalbanking_backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.digitalbanking_backend.entities.BankAccount;
import org.sid.digitalbanking_backend.entities.CurrentAccount;
import org.sid.digitalbanking_backend.entities.Customer;
import org.sid.digitalbanking_backend.entities.SavingAccount;
import org.sid.digitalbanking_backend.exceptions.BankAccountNotFoundException;
import org.sid.digitalbanking_backend.exceptions.CustomerNotFoundException;
import org.sid.digitalbanking_backend.repositories.AccountOperationRepository;
import org.sid.digitalbanking_backend.repositories.BankAccountRepository;
import org.sid.digitalbanking_backend.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;


    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("Saving new customer");
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        CurrentAccount currentAccount=new CurrentAccount();

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        CurrentAccount savedBankAccount =bankAccountRepository.save(currentAccount);
        return savedBankAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        SavingAccount savingAccount=new SavingAccount();

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        SavingAccount savedBankAccount =bankAccountRepository.save(savingAccount);
        return savedBankAccount;
    }


    @Override
    public List<Customer> listCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        return null;
    }

    @Override
    public void debit(String accountId, double amount, String description) {

    }

    @Override
    public void credit(String accountId, double amount, String description) {

    }

    @Override
    public void transfer(String fromAccountId, String accountIdSource, String accountIdDescription, double amount) {

    }
}
