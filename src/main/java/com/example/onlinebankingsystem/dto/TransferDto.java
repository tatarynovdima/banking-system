package com.example.onlinebankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransferDto {
    private String recipientCardNumber;
    private BigDecimal amount;
}