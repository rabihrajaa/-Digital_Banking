package org.sid.digitalbanking_backend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.digitalbanking_backend.dtos.CustomerDTO;
import org.sid.digitalbanking_backend.entities.Customer;
import org.sid.digitalbanking_backend.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers () {
        return bankAccountService.listCustomers();
    }
}
