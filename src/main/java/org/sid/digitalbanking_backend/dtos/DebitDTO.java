package org.sid.digitalbanking_backend.dtos;

import lombok.Data;

@Data
public class DebitDTO {
    private int accountId;
    private double amount;
    private String description;
}
