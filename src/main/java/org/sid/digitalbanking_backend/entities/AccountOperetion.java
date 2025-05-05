package org.sid.digitalbanking_backend.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.digitalbanking_backend.enums.OperationType;

import java.util.Date;
@Data @NoArgsConstructor
@AllArgsConstructor
public class AccountOperetion {
    private Long id;
    private Date opeartionDate;
    private double amount;
    private OperationType type;
    private BankAccount bankAccount;
}
