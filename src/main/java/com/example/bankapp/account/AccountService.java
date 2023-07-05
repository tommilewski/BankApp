package com.example.bankapp.account;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    @Transactional
    public void transfer(Account from_account, Account to_account, BigDecimal amount) {
        validateAmount(amount);

        Account fromAccount = accountRepository.findById(from_account.getId()).get();

        Account toAccount = accountRepository.findById(to_account.getId()).orElseThrow(() ->
                new IllegalArgumentException("Account does not exist"));

        validateAccounts(fromAccount.getId(), toAccount.getId());

        BigDecimal fromAccountResult = fromAccount.getBalance().subtract(amount);
        if (fromAccountResult.compareTo(new BigDecimal("0")) > 0){
            fromAccount.setBalance(fromAccountResult);
            BigDecimal toAccountResult = toAccount.getBalance().add(amount);
            toAccount.setBalance(toAccountResult);
        } else {
            throw new IllegalArgumentException("Not enough money");
        }

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

    }

    private void validateAmount(BigDecimal amount){
        if (amount.compareTo(new BigDecimal("0")) <= 0){
            throw new IllegalArgumentException("Amount cannot be smaller than 0");
        }
    }
    private void validateAccounts(long fromAccountId, long toAccountId){
        if (fromAccountId == toAccountId){
            throw new IllegalArgumentException("Accounts are the same");
        }
    }


}
