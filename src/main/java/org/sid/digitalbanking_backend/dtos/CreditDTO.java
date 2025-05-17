package org.sid.digitalbanking_backend.dtos;

import lombok.Data;

@Data
public class CreditDTO {
    private int accountId;
    private double amount;
    private String description;
}
