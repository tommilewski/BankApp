package com.example.bankapp.credit;

import com.example.bankapp.account.Account;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequest {
    private Account toAccount;
    private BigDecimal amount;
    private int installments;
    private BigDecimal monthlyPayment;
}
