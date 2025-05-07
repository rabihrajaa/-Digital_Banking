package org.sid.digitalbanking_backend.dtos;

import org.sid.digitalbanking_backend.enums.AccountStatus;

import java.util.Date;

public class BankAccountDTO {
    private String id;
    private double balance;
    private Date CreatedAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
}
