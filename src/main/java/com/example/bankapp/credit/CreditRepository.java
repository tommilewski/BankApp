package com.example.bankapp.credit;

import com.example.bankapp.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    List<Credit> findAllByToAccount(Account account);
}
