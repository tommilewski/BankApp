package com.example.bankapp.registration;

import com.example.bankapp.account.Account;
import com.example.bankapp.account.AccountService;
import com.example.bankapp.user.Role;
import com.example.bankapp.user.User;
import com.example.bankapp.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final AccountService accountService;

    @Transactional
    public User register(RegistrationRequest request) {
        Account account = new Account(new BigDecimal("1000"));
        accountService.saveAccount(account);
        User appUser = new User(
                request.getName(),
                request.getSurname(),
                request.getEmail(),
                request.getPassword(),
                Role.USER,
                account);

        return userService.signUpUser(appUser);

    }
}
