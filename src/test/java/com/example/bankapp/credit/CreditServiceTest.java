package com.example.bankapp.credit;

import com.example.bankapp.account.Account;
import com.example.bankapp.account.AccountService;
import com.example.bankapp.utils.TimeUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private TimeUtils timeUtils;
    @InjectMocks
    private CreditService creditService;

    @Captor
    private ArgumentCaptor<Credit> creditCaptor;

    @Test
    void takeMoneyBack_ShouldUpdateCreditsCorrectly() {
        OffsetDateTime currentTime = OffsetDateTime.of(2023, 7, 15, 12, 30, 0, 0, ZoneOffset.UTC);

        OffsetDateTime creditTime = OffsetDateTime.of(2023, 6, 15, 12, 30, 0, 0, ZoneOffset.UTC);

        Credit credit1 = new Credit(new Account(new BigDecimal("5000")), new BigDecimal("1000"), 10, creditTime, new BigDecimal("102.31"));
        Credit credit2 = new Credit(new Account(new BigDecimal("5000")), new BigDecimal("10000"), 26, creditTime, new BigDecimal("429.38"));

        List<Credit> credits = new ArrayList<>();
        credits.add(credit1);
        credits.add(credit2);

        when(timeUtils.getCurrentlyTime()).thenReturn(currentTime);
        when(creditRepository.findAll()).thenReturn(credits);

        creditService.takeMoneyBack();


        verify(creditRepository, times(2)).save(creditCaptor.capture());

        List<Credit> capturedCredits = creditCaptor.getAllValues();

        Credit updatedCredit1 = capturedCredits.get(0);
        assertEquals(new BigDecimal("4897.69"), updatedCredit1.getToAccount().getBalance());
        assertEquals(9, updatedCredit1.getRemainingInstallments());

        Credit updatedCredit2 = capturedCredits.get(1);
        assertEquals(new BigDecimal("4570.62"), updatedCredit2.getToAccount().getBalance());
        assertEquals(25, updatedCredit2.getRemainingInstallments());
    }

    @Test
    void takeMoneyBack_ShouldUpdateCreditsAndDeleteCorrectly() {
        OffsetDateTime currentTime = OffsetDateTime.of(2023, 7, 15, 12, 30, 0, 0, ZoneOffset.UTC);

        OffsetDateTime creditTime = OffsetDateTime.of(2023, 6, 15, 12, 30, 0, 0, ZoneOffset.UTC);

        Credit credit1 = new Credit(new Account(new BigDecimal("5000")), new BigDecimal("1000"), 1, creditTime, new BigDecimal("1004.17"));
        Credit credit2 = new Credit(new Account(new BigDecimal("50000")), new BigDecimal("10000"), 1, creditTime, new BigDecimal("10041.67"));

        List<Credit> credits = new ArrayList<>();
        credits.add(credit1);
        credits.add(credit2);

        when(timeUtils.getCurrentlyTime()).thenReturn(currentTime);
        when(creditRepository.findAll()).thenReturn(credits);

        creditService.takeMoneyBack();


        verify(creditRepository, times(2)).save(creditCaptor.capture());

        List<Credit> capturedCredits = creditCaptor.getAllValues();

        Credit updatedCredit1 = capturedCredits.get(0);
        assertEquals(new BigDecimal("3995.83"), updatedCredit1.getToAccount().getBalance());
        assertEquals(0, updatedCredit1.getRemainingInstallments());

        Credit updatedCredit2 = capturedCredits.get(1);
        assertEquals(new BigDecimal("39958.33"), updatedCredit2.getToAccount().getBalance());
        assertEquals(0, updatedCredit2.getRemainingInstallments());

        verify(creditRepository, times(1)).delete(eq(credit1));
        verify(creditRepository, times(1)).delete(eq(credit2));
    }


}