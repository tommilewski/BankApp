package com.example.bankapp.registration;


import com.example.bankapp.account.Account;
import com.example.bankapp.account.AccountService;
import com.example.bankapp.user.Role;
import com.example.bankapp.user.User;
import com.example.bankapp.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private AccountService accountService;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void register_SuccessfullyRegistersUser() {

        Account account = new Account(new BigDecimal("1000"));

        RegistrationRequest request = new RegistrationRequest("Jan", "Kowalski",
                "jan.kowalski@gmail.com", "password");


        User appUser = new User(
                request.getName(),
                request.getSurname(),
                request.getEmail(),
                request.getPassword(),
                Role.USER,
                account);

        doNothing().when(accountService).saveAccount(any(Account.class));

        when(userService.signUpUser(any(User.class))).thenReturn(appUser);

        User result = registrationService.register(request);

        assertThat(result.getName()).isEqualTo("Jan");
        assertThat(result.getSurname()).isEqualTo("Kowalski");
        assertThat(result.getEmail()).isEqualTo("jan.kowalski@gmail.com");
        assertThat(result.getPassword()).isEqualTo("password");

    }
}