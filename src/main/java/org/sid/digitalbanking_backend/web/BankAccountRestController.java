package org.sid.digitalbanking_backend.web;

import lombok.AllArgsConstructor;
import org.sid.digitalbanking_backend.services.BankAccountService;
import org.springframework.web.bind.annotation.RestController;

@RestController @AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;



}
