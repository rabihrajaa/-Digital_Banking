package org.sid.digitalbanking_backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.digitalbanking_backend.dtos.*;
import org.sid.digitalbanking_backend.entities.*;
import org.sid.digitalbanking_backend.enums.OperationType;
import org.sid.digitalbanking_backend.exceptions.BankAccountNotFoundException;
import org.sid.digitalbanking_backend.exceptions.BankAccountNotSufficientException;
import org.sid.digitalbanking_backend.exceptions.CustomerNotFoundException;
import org.sid.digitalbanking_backend.mappers.BankAccountMapperImpl;
import org.sid.digitalbanking_backend.repositories.AccountOperationRepository;
import org.sid.digitalbanking_backend.repositories.BankAccountRepository;
import org.sid.digitalbanking_backend.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        log.info("customerDTO reçu: {}", customerDTO);
        Customer customer=bankAccountMapper.fromCustomerDTOToCustomer(customerDTO);
        log.info("customer: {}", customer);
        Customer savedCustomer = customerRepository.save(customer);
        log.info("customerDTO : {}", bankAccountMapper.fromCustomerToCustomerDTO(savedCustomer));
        return bankAccountMapper.fromCustomerToCustomerDTO(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
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
        return bankAccountMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
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
        return bankAccountMapper.fromSavingBankAccount(savedBankAccount);
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
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount= (SavingAccount) bankAccount;
            return bankAccountMapper.fromSavingBankAccount(savingAccount);
        } else {
            CurrentAccount currentAccount= (CurrentAccount) bankAccount;
            return bankAccountMapper.fromCurrentBankAccount(currentAccount);
        }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BankAccountNotSufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));        if(bankAccount.getBalance() < amount) throw new BankAccountNotSufficientException("Not enough balance");
        AccountOperetion accountOperetion=new AccountOperetion();
        accountOperetion.setType(OperationType.DEBIT);
        accountOperetion.setAmount(amount);
        accountOperetion.setDescription(description);
        accountOperetion.setOperationDate(new Date());
        accountOperetion.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperetion);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));        AccountOperetion accountOperetion=new AccountOperetion();
        accountOperetion.setType(OperationType.CREDIT);
        accountOperetion.setAmount(amount);
        accountOperetion.setDescription(description);
        accountOperetion.setOperationDate(new Date());
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
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return bankAccountMapper.fromSavingBankAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return bankAccountMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        log.info("customer: {}", customerId);
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("Customer not found"));
            log.info("customer: {}", customer);
        log.info("customerDTO: {}", bankAccountMapper.fromCustomerToCustomerDTO(customer));
        return bankAccountMapper.fromCustomerToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer");
        log.info("customerDTO reçu: {}", customerDTO);
        Customer customer=bankAccountMapper.fromCustomerDTOToCustomer(customerDTO);
        log.info("customer: {}", customer);
        Customer savedCustomer = customerRepository.save(customer);
        log.info("customerDTO : {}", bankAccountMapper.fromCustomerToCustomerDTO(savedCustomer));
        return bankAccountMapper.fromCustomerToCustomerDTO(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<AccountOperetionDTO> accountHistory(String accountId){
        List<AccountOperetion> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        return accountOperations.stream().map(op->bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
        Page<AccountOperetion> accountOperations = accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperetionDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCutomers(String keyword) {
        List<Customer> customers = customerRepository.findByNameContains(keyword);
        List<CustomerDTO> cutomerDto= customers.stream().map(cust->bankAccountMapper.fromCustomerToCustomerDTO(cust)).collect(Collectors.toList());
        return cutomerDto;
    }

}
