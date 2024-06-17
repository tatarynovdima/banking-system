package com.example.onlinebankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CardDto {
    private Long id;
    private String cardNumber;
    private LocalDate releaseDate;
    private BigDecimal balance;
}