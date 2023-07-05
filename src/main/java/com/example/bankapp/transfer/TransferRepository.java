package com.example.bankapp.transfer;

import com.example.bankapp.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findAllByFromAccount(Account fromAccount);
    List<Transfer> findAllByToAccount(Account toAccount);
}

