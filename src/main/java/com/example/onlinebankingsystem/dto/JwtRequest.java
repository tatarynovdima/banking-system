package com.example.onlinebankingsystem.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String email;
    private String password;
}