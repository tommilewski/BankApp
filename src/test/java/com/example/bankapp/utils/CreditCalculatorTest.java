package com.example.bankapp.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreditCalculatorTest {

    private CreditCalculator creditCalculator;

    @BeforeEach
    void setUp() {
        creditCalculator = new CreditCalculator();
    }

    @Test
    void calculateMonthlyPayment_LowRates_CorrectCalculation() {
        BigDecimal amount = new BigDecimal("1000");
        int rates = 6;

        BigDecimal monthlyPayment = creditCalculator.calculateMonthlyPayment(amount, rates);

        BigDecimal expectedPayment = new BigDecimal("169.11");
        assertEquals(expectedPayment, monthlyPayment);
    }

    @Test
    void calculateMonthlyPayment_MidRates_CorrectCalculation() {
        BigDecimal amount = new BigDecimal("1000");
        int rates = 20;

        BigDecimal monthlyPayment = creditCalculator.calculateMonthlyPayment(amount, rates);

        BigDecimal expectedPayment = new BigDecimal("53.12");
        assertEquals(expectedPayment, monthlyPayment);
    }


    @Test
    void calculateMonthlyPayment_HighRates_CorrectCalculation() {
        BigDecimal amount = new BigDecimal("1000");
        int rates = 36;

        BigDecimal monthlyPayment = creditCalculator.calculateMonthlyPayment(amount, rates);

        BigDecimal expectedPayment = new BigDecimal("32.27");
        assertEquals(expectedPayment, monthlyPayment);
    }
}