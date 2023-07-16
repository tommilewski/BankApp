package com.example.bankapp.transfer;

import com.example.bankapp.account.Account;
import com.example.bankapp.account.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransferService transferService;

    @Test
    public void findAllByFromAccount_ReturnsListOfTransfers() {

        Account account = new Account(new BigDecimal("1000"));
        Transfer transfer1 = new Transfer(account, new Account(new BigDecimal("10")), new BigDecimal("50"), "test", OffsetDateTime.now());
        Transfer transfer2 = new Transfer(account, new Account(new BigDecimal("150")), new BigDecimal("200"), "test", OffsetDateTime.now());

        List<Transfer> expectedTransfers = Arrays.asList(transfer1, transfer2);

        when(transferRepository.findAllByFromAccount(account)).thenReturn(expectedTransfers);

        List<Transfer> actualTransfers = transferService.findAllByFromAccount(account);

        assertEquals(expectedTransfers, actualTransfers);
    }

    @Test
    public void findAllByToAccount_ReturnsListOfTransfers() {

        Account account = new Account(new BigDecimal("1000"));
        Transfer transfer1 = new Transfer(account, new Account(new BigDecimal("10")), new BigDecimal("50"), "test", OffsetDateTime.now());
        Transfer transfer2 = new Transfer(account, new Account(new BigDecimal("150")), new BigDecimal("200"), "test", OffsetDateTime.now());

        List<Transfer> expectedTransfers = Arrays.asList(transfer1, transfer2);

        when(transferRepository.findAllByToAccount(account)).thenReturn(expectedTransfers);

        List<Transfer> actualTransfers = transferService.findAllByToAccount(account);

        assertEquals(expectedTransfers, actualTransfers);
    }

    @Test
    void makeTransfer_SuccessfullyMakesTransfer() {
        Account fromAccount = new Account(new BigDecimal("1000"));
        Account toAccount = new Account(new BigDecimal("100"));

        BigDecimal amount = new BigDecimal("100.0");
        String description = "Transfer description";

        TransferRequest transferRequest = new TransferRequest(amount, fromAccount, toAccount, description);

        doNothing().when(accountService).transfer(fromAccount, toAccount, amount);


        transferService.makeTransfer(transferRequest);

        verify(accountService, times(1)).transfer(fromAccount, toAccount, amount);
    }
}

