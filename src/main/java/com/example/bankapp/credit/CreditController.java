package com.example.bankapp.credit;

import com.example.bankapp.account.AccountService;
import com.example.bankapp.user.UserService;
import com.example.bankapp.utils.CreditCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;
    private final UserService userService;

    private final CreditCalculator creditCalculator;

    @GetMapping("/take-credit")
    public String credit(){
        return "credit-form";
    }


    @PostMapping("/take-credit")
    public String takeCredit(CreditRequest creditRequest){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        BigDecimal monthlyPayments = creditCalculator.calculateMonthlyPayment(creditRequest.getAmount(), creditRequest.getRates());
        creditRequest.setMonthlyPayment(monthlyPayments);

        creditRequest.setToAccount(userService.findAppUserByEmail(email).getAccount());
        creditService.takeCredit(creditRequest);

        return "redirect:/";
    }
}
