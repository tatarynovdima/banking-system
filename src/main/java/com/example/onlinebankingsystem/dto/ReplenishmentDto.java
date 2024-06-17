package com.example.onlinebankingsystem.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class ReplenishmentDto {
    private BigDecimal amount;
}
