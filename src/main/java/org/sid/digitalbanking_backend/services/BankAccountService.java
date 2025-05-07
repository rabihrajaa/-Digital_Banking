package org.sid.digitalbanking_backend.services;

import org.sid.digitalbanking_backend.dtos.BankAccountDTO;
import org.sid.digitalbanking_backend.dtos.CurrentBankAccountDTO;
import org.sid.digitalbanking_backend.dtos.CustomerDTO;
import org.sid.digitalbanking_backend.dtos.SavingBankAccountDTO;
import org.sid.digitalbanking_backend.entities.BankAccount;
import org.sid.digitalbanking_backend.entities.CurrentAccount;
import org.sid.digitalbanking_backend.entities.Customer;
import org.sid.digitalbanking_backend.entities.SavingAccount;
import org.sid.digitalbanking_backend.exceptions.BankAccountNotFoundException;
import org.sid.digitalbanking_backend.exceptions.BankAccountNotSufficientException;
import org.sid.digitalbanking_backend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    public CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount,String description) throws BankAccountNotFoundException, BankAccountNotSufficientException;
    void credit(String accountId, double amount,String description) throws BankAccountNotFoundException;
    void transfer(String fromAccountId, String accountIdSource,String accountIdDescription, double amount) throws BankAccountNotFoundException, BankAccountNotSufficientException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);
}
