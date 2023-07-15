package com.example.bankapp.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CreditCalculator {
    public BigDecimal calculateMonthlyPayment(BigDecimal amount, int rates) {

        BigDecimal interestRate = getInterestRate(rates);
        return calculateMonthlyPayment(amount, rates, interestRate);

    }

    private BigDecimal getInterestRate(int rates) {
        if (rates <= 12) {
            return new BigDecimal("5"); // 5%
        } else if (rates <= 24) {
            return new BigDecimal("7"); // 7%
        } else {
            return new BigDecimal("10"); // 10%
        }
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal amount, int rates, BigDecimal interestRate) {
        BigDecimal interest = interestRate.divide(new BigDecimal("100"), 2, RoundingMode.DOWN);
        BigDecimal monthlyInterest = interest.divide(new BigDecimal("12"), 50, RoundingMode.DOWN);
        BigDecimal denominator = monthlyInterest.add(BigDecimal.ONE).pow(rates).subtract(BigDecimal.ONE);
        return amount.multiply(monthlyInterest).multiply(monthlyInterest.add(BigDecimal.ONE).pow(rates)).divide(denominator, 2, RoundingMode.HALF_DOWN);
    }
}
