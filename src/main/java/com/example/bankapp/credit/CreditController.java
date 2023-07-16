package com.example.bankapp.credit;

import com.example.bankapp.account.Account;
import com.example.bankapp.user.UserService;
import com.example.bankapp.utils.CreditCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;
    private final UserService userService;

    private final CreditCalculator creditCalculator;

    @GetMapping("/take-credit")
    public String credit(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = userService.findAppUserByEmail(email).getAccount();

        List<Credit> creditsByAccount = creditService.findCreditsByAccount(account);

        model.addAttribute("loans", creditsByAccount);
        return "credit-form";
    }

    @PostMapping("/take-credit")
    public String takeCredit(CreditRequest creditRequest){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = userService.findAppUserByEmail(email).getAccount();

        BigDecimal monthlyPayments = creditCalculator.calculateMonthlyPayment(creditRequest.getAmount(), creditRequest.getInstallments());
        creditRequest.setMonthlyPayment(monthlyPayments);

        creditRequest.setToAccount(account);
        creditService.takeCredit(creditRequest);

        return "redirect:/";
    }
}
