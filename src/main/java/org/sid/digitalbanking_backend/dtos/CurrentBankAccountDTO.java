package org.sid.digitalbanking_backend.dtos;

import lombok.Data;
import org.sid.digitalbanking_backend.enums.AccountStatus;

import java.util.Date;

@Data
public class CurrentBankAccountDTO extends BankAccountDTO {

    private double overdraft;
}
