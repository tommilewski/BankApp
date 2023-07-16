package com.example.bankapp.user;


import com.example.bankapp.account.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void signUp_AppUser_correctly() {
        User appUser = new User(1L, "Jan", "Nowak", "jan.nowak@gmail.com",
                "password", Role.USER, new Account());

        when(userRepository.findAppUserByEmail("jan.nowak@gmail.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(appUser)).thenReturn(appUser);

        User result = userService.signUpUser(appUser);

        assertThat(result).isEqualTo(appUser);
    }

    @Test
    void singUp_AppUser_with_email_exists() {
        User appUser = new User(1L, "Jan", "Nowak", "jan.nowak@gmail.com",
                "password", Role.USER, new Account());

        when(userRepository.findAppUserByEmail("jan.nowak@gmail.com")).thenReturn(Optional.of(appUser));

        assertThrows(IllegalArgumentException.class,
                () -> userService.signUpUser(appUser));
    }

    @Test
    void find_AppUser_by_email_correctly() {
        User appUser = new User(1L, "Jan", "Nowak", "jan.nowak@gmail.com",
                "password", Role.USER, new Account());

        when(userRepository.findAppUserByEmail("jan.nowak@gmail.com")).thenReturn(Optional.of(appUser));

        User result = userService.findAppUserByEmail("jan.nowak@gmail.com");

        assertThat(result).isEqualTo(appUser);
    }

    @Test
    void find_AppUser_by_email_incorrectly() {
        User appUser = new User(1L, "Jan", "Nowak", "jan.nowak@gmail.com",
                "password", Role.USER, new Account());

        when(userRepository.findAppUserByEmail("jan.nowak@gmail.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userService.findAppUserByEmail("jan.nowak@gmail.com"));
    }
}