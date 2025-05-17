package org.sid.digitalbanking_backend.dtos;


import lombok.Data;
import org.sid.digitalbanking_backend.enums.OperationType;

import java.util.Date;

@Data
public class AccountOperetionDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
