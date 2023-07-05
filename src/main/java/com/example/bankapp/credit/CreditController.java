package com.example.bankapp.credit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreditController {

    @GetMapping("/take-credit")
    public String credit(){
        return "credit-form";
    }
}
