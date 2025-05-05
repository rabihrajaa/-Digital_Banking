package org.sid.digitalbanking_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.digitalbanking_backend.enums.AccountStatus;

import java.util.Date;
import java.util.List;
@Data @NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    private String id;
    private double balance;
    private Date CreatedAt;
    private AccountStatus status;
    private Customer customer;
    private List<AccountOperetion> operetions;
}
