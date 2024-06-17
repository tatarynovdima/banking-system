package com.example.onlinebankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullNameBalanceDTO {
    private String fullName;
    private BigDecimal balance;

    public FullNameBalanceDTO(String firstName, String lastName, BigDecimal balance) {
        this.fullName = firstName + " " + lastName;
        this.balance = balance;
    }
}
