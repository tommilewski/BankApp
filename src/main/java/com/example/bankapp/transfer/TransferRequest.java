package com.example.bankapp.transfer;

import com.example.bankapp.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferRequest {

    private BigDecimal amount;
    private Account fromAccount;
    private Account toAccount;
    private String description;
}
