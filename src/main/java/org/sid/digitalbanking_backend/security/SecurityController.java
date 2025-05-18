package org.sid.digitalbanking_backend.security;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    @GetMapping("/profile")
    public Authentication getAuthentication(Authentication authentication) {
        return authentication;
    }

}
