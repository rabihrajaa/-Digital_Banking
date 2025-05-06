package org.sid.digitalbanking_backend.services;

import org.sid.digitalbanking_backend.entities.BankAccount;
import org.sid.digitalbanking_backend.entities.Customer;
import org.sid.digitalbanking_backend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    public Customer saveCustomer(Customer customer);
    BankAccount saveCurrentBankAccount(double initialBalance,double overDraft,Long customerId) throws CustomerNotFoundException;
    BankAccount saveSavingBankAccount(double initialBalance,double interestRate,Long customerId) throws CustomerNotFoundException;
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId);
    void debit(String accountId, double amount,String description);
    void credit(String accountId, double amount,String description);
    void transfer(String fromAccountId, String accountIdSource,String accountIdDescription, double amount);

}
