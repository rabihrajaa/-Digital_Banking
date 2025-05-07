package org.sid.digitalbanking_backend.mappers;

import org.sid.digitalbanking_backend.dtos.CurrentBankAccountDTO;
import org.sid.digitalbanking_backend.dtos.CustomerDTO;
import org.sid.digitalbanking_backend.dtos.SavingBankAccountDTO;
import org.sid.digitalbanking_backend.entities.CurrentAccount;
import org.sid.digitalbanking_backend.entities.Customer;
import org.sid.digitalbanking_backend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

//MapStruct
@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO=new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        //customerDTO.setId(customer.getId());
        //customerDTO.setName (customer.getName());
        //customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }

    public Customer fromCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;    }

    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount) {
        SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO();
        BeanUtils.copyProperties (savingAccount, savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCustomerToCustomerDTO(savingAccount.getCustomer()));
        return savingBankAccountDTO;
    }

    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO) {
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties (savingBankAccountDTO, savingAccount);
        savingAccount.setCustomer(fromCustomerDTOToCustomer(savingBankAccountDTO.getCustomerDTO()));
        return savingAccount;
    }

    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount) {
        CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
        BeanUtils.copyProperties (currentAccount, currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomerToCustomerDTO(currentAccount.getCustomer()));
        return currentBankAccountDTO;
    }

    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO) {
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties (currentBankAccountDTO, currentAccount);
        currentAccount.setCustomer(fromCustomerDTOToCustomer(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }


}
