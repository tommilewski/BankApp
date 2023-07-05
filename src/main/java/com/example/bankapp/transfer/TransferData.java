package com.example.bankapp.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransferData {

    private String fromEmail;
    private String toEmail;
    private BigDecimal amount;
    private String description;

}
