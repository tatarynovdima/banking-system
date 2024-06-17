package com.example.onlinebankingsystem.dto;

import lombok.Data;

@Data
public class RegistrationClientDto {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    private String countryName;
}
