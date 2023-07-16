package com.example.bankapp.account;

import com.example.bankapp.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void transfer_money_correctly() {
        //given
        User user1 = new User();
        User user2 = new User();
        Account fromAccount = new Account(1L, new BigDecimal("1000"), user1);
        Account toAccount = new Account(2L, new BigDecimal("500"), user2);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        //when
        accountService.transfer(fromAccount, toAccount, new BigDecimal("100"));


        //then
        ArgumentCaptor<Account> argumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository, times(2)).save(argumentCaptor.capture());

        List<Account> allValues = argumentCaptor.getAllValues();

        assertThat(allValues.get(0).getBalance()).isEqualTo(new BigDecimal("900"));
        assertThat(allValues.get(1).getBalance()).isEqualTo(new BigDecimal("600"));
    }

    @Test
    public void transfer_money_with_amount_smaller_than_zero() {
        //given
        User user1 = new User();
        User user2 = new User();
        Account fromAccount = new Account(1L, new BigDecimal("1000"), user1);
        Account toAccount = new Account(2L, new BigDecimal("500"), user2);

        //when
        //then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> accountService.transfer(fromAccount, toAccount, new BigDecimal("-100")));
    }

    @Test
    public void transfer_money_with_not_exist_toAccount() {
        //given
        User user1 = new User();
        User user2 = new User();
        Account fromAccount = new Account(1L, new BigDecimal("1000"), user1);
        Account toAccount = new Account(2L, new BigDecimal("500"), user2);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.empty());


        //when
        //then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> accountService.transfer(fromAccount, toAccount, new BigDecimal("100")));
    }

    @Test
    public void transfer_money_with_not_enough_balance() {
        //given
        User user1 = new User();
        User user2 = new User();
        Account fromAccount = new Account(1L, new BigDecimal("100"), user1);
        Account toAccount = new Account(2L, new BigDecimal("500"), user2);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));


        //when
        //then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> accountService.transfer(fromAccount, toAccount, new BigDecimal("1000")));
    }

    @Test
    public void transfer_money_to_your_account() {
        //given
        User user = new User();
        Account account = new Account(1L, new BigDecimal("1000"), user);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        //when
        //then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> accountService.transfer(account, account, new BigDecimal("100")));
    }

    @Test
    public void save_account_correctly() {
        //given
        User user = new User();
        Account account = new Account(1L, new BigDecimal("1000"), user);

        when(accountRepository.save(account)).thenReturn(account);

        //when
        accountService.saveAccount(account);
        //then
        ArgumentCaptor<Account> argumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository, times(1)).save(argumentCaptor.capture());
        assertThat(account).isEqualTo(argumentCaptor.getValue());
    }

    @Test
    public void take_credit_correctly() {
        Account account = new Account(new BigDecimal("1000"));
        BigDecimal amount = new BigDecimal("500");

        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        accountService.takeCredit(account, amount);

        assertEquals(new BigDecimal("1500"), account.getBalance());
        verify(accountRepository, times(1)).findById(account.getId());
        verify(accountRepository, times(1)).save(account);

    }
}