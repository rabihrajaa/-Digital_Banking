package org.sid.digitalbanking_backend.dtos;

import lombok.Data;
import org.sid.digitalbanking_backend.enums.AccountStatus;

import java.util.Date;
@Data
public class BankAccountDTO {
    private String id;
    private double balance;
    private Date CreatedAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private String type;
}
