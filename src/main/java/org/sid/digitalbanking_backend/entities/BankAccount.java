package org.sid.digitalbanking_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.digitalbanking_backend.enums.AccountStatus;

import java.util.Date;
import java.util.List;
@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date CreatedAt;
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount")
    private List<AccountOperetion> operetions;
}
