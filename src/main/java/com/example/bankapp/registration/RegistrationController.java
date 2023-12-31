package com.example.bankapp.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping("/register")
    public String register(){
        return "register-form";
    }

    @PostMapping("/register")
    public String processRegistrationForm(RegistrationRequest registrationRequest) {
        registrationService.register(registrationRequest);
        return "redirect:/";
    }
}
