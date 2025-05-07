package org.sid.digitalbanking_backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.digitalbanking_backend.dtos.CustomerDTO;
import org.sid.digitalbanking_backend.entities.*;
import org.sid.digitalbanking_backend.enums.OperationType;
import org.sid.digitalbanking_backend.exceptions.BankAccountNotFoundException;
import org.sid.digitalbanking_backend.exceptions.BankAccountNotSufficientException;
import org.sid.digitalbanking_backend.exceptions.CustomerNotFoundException;
import org.sid.digitalbanking_backend.mappers.BankAccountMapperImpl;
import org.sid.digitalbanking_backend.repositories.AccountOperationRepository;
import org.sid.digitalbanking_backend.repositories.BankAccountRepository;
import org.sid.digitalbanking_backend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl bankAccountMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer");
        Customer customer=bankAccountMapper.fromCustomerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return bankAccountMapper.fromCustomerToCustomerDTO(savedCustomer);
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
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers=  (List<Customer>) customerRepository.findAll();
        List<CustomerDTO> collect = customers.stream()
                .map(customer -> bankAccountMapper.fromCustomerToCustomerDTO(customer))
                .collect(Collectors.toList());
        /*
        List<CustomerDTO> customerD TOS=new ArrayList<>();
        for (Customer customer:customers) {
        Customer DTO customerDTO=dtoMapper.fromCustomer (customer);
        customerDTOS.add(customerDTO);
        */
        return collect;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        return null;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BankAccountNotSufficientException {
        BankAccount bankAccount=getBankAccount(accountId);
        if(bankAccount.getBalance() < amount) throw new BankAccountNotSufficientException("Not enough balance");
        AccountOperetion accountOperetion=new AccountOperetion();
        accountOperetion.setType(OperationType.DEBIT);
        accountOperetion.setAmount(amount);
        accountOperetion.setDescription(description);
        accountOperetion.setOpeartionDate(new Date());
        accountOperetion.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperetion);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=getBankAccount(accountId);
        AccountOperetion accountOperetion=new AccountOperetion();
        accountOperetion.setType(OperationType.CREDIT);
        accountOperetion.setAmount(amount);
        accountOperetion.setDescription(description);
        accountOperetion.setOpeartionDate(new Date());
        accountOperetion.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperetion);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String fromAccountId, String accountIdSource, String accountIdDescription, double amount) throws BankAccountNotFoundException, BankAccountNotSufficientException {
        debit(accountIdSource,amount,"Transfer to "+accountIdDescription);
        credit(accountIdDescription,amount,"Transfer from "+accountIdDescription);
    }
    @Override
    public List<BankAccount> bankAccountList(){
        return (List<BankAccount>) bankAccountRepository.findAll();
    }
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("Customer not found"));
        return bankAccountMapper.fromCustomerToCustomerDTO(customer);
    }



}
