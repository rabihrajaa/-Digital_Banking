package org.sid.digitalbanking_backend.services;

import org.sid.digitalbanking_backend.entities.BankAccount;
import org.sid.digitalbanking_backend.entities.Customer;

import java.util.List;

public interface BankAccountService {
    public Customer saveCustomer(Customer customer);
    BankAccount saveBankAccount(double initialBalance, String type,Long customerId);
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId);
    void debit(String accountId, double amount,String description);
    void credit(String accountId, double amount,String description);
    void transfer(String fromAccountId, String accountIdSource,String accountIdDescription, double amount);
    
}
