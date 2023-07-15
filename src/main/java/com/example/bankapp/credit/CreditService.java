package com.example.bankapp.credit;

import com.example.bankapp.account.AccountService;
import com.example.bankapp.utils.TimeUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;
    private final AccountService accountService;

    private final TimeUtils timeUtils;

    @Transactional
    public void takeCredit(CreditRequest creditRequest) {

        accountService.takeCredit(creditRequest.getToAccount(), creditRequest.getAmount());

        Credit credit = new Credit(
                creditRequest.getToAccount(),
                creditRequest.getAmount(),
                creditRequest.getInstallments(),
                OffsetDateTime.now(),
                creditRequest.getMonthlyPayment()
        );

        creditRepository.save(credit);

    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void takeMoneyBack() {
        OffsetDateTime currentTime = timeUtils.getCurrentlyTime();

        List<Credit> credits = creditRepository.findAll();
        credits.stream()
                .filter(credit -> credit.getStartTime().getDayOfMonth() == currentTime.getDayOfMonth())
                .filter(credit -> credit.getStartTime()
                        .plusMonths(credit.getInstallments() - credit.getRemainingInstallments() + 1).getMonthValue() == currentTime.getMonthValue())
                .forEach(credit -> {
                    credit.getToAccount().setBalance(new BigDecimal(String.valueOf(credit.getToAccount().getBalance().subtract(credit.getMonthlyPayment()))));
                    credit.setRemainingInstallments(credit.getRemainingInstallments() - 1);
                    creditRepository.save(credit);
                });

        deleteRepaidCredits();
    }

    @Transactional
    private void deleteRepaidCredits() {
        List<Credit> credits = creditRepository.findAll();
        credits.stream()
                .filter(credit -> credit.getRemainingInstallments() == 0)
                .forEach(creditRepository::delete);
    }
}
