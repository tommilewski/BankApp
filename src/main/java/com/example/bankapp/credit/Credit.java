package com.example.bankapp.credit;

import com.example.bankapp.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "credits")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;
    private BigDecimal amount;
    private int rates;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;

    private BigDecimal monthlyPayment;

    public Credit(Account toAccount, BigDecimal amount, int rates, OffsetDateTime startTime, BigDecimal monthlyPayment) {
        this.toAccount = toAccount;
        this.amount = amount;
        this.rates = rates;
        this.startTime = startTime;
        endTime = startTime.plusMonths(rates);
        this.monthlyPayment = monthlyPayment;
    }
}
