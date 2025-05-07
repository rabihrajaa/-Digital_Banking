package org.sid.digitalbanking_backend.dtos;

import lombok.Data;
import org.sid.digitalbanking_backend.enums.AccountStatus;

import java.util.Date;

@Data
public class SavingBankAccountDTO extends BankAccountDTO {
    private double interestRate;
}
