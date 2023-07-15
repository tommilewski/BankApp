package com.example.bankapp.credit;

import com.example.bankapp.account.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;
    private final AccountService accountService;

    @Transactional
    public void takeCredit(CreditRequest creditRequest) {

        accountService.takeCredit(creditRequest.getToAccount(), creditRequest.getAmount());

        Credit credit = new Credit(
                creditRequest.getToAccount(),
                creditRequest.getAmount(),
                creditRequest.getRates(),
                OffsetDateTime.now(),
                creditRequest.getMonthlyPayment()
        );

        creditRepository.save(credit);

    }
}
