package com.example.bankapp.transfer;

import com.example.bankapp.account.Account;
import com.example.bankapp.account.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final AccountService accountService;

    public List<Transfer> findAllByFromAccount(Account account){
        return transferRepository.findAllByFromAccount(account);
    }

    public List<Transfer> findAllByToAccount(Account account){
        return transferRepository.findAllByToAccount(account);
    }


    @Transactional
    public void makeTransfer(TransferRequest transferRequest) {

        accountService.transfer(
                transferRequest.getFromAccount(),
                transferRequest.getToAccount(),
                transferRequest.getAmount()
        );

        Transfer transfer = new Transfer(
                transferRequest.getFromAccount(),
                transferRequest.getToAccount(),
                transferRequest.getAmount(),
                transferRequest.getDescription(),
                OffsetDateTime.now()
        );

        transferRepository.save(transfer);

    }

}
