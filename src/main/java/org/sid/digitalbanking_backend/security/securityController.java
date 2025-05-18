package org.sid.digitalbanking_backend.security;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Authenticator;

@RestController
@RequestMapping("/auth")
public class securityController {
    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication) {
        return authentication;
    }

}
