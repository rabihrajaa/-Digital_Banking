package org.sid.digitalbanking_backend.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.digitalbanking_backend.enums.OperationType;

import java.util.Date;
@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
public class AccountOperetion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date opeartionDate;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    private BankAccount bankAccount;
    private String description;
}
