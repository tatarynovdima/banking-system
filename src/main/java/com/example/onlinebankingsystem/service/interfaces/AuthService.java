package com.example.onlinebankingsystem.service.interfaces;

import com.example.onlinebankingsystem.dto.JwtRequest;
import com.example.onlinebankingsystem.dto.RegistrationClientDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> createAuthToken(JwtRequest authRequest);
    ResponseEntity<?> createNewUser(RegistrationClientDto registrationUserDto);

    ResponseEntity<?> confirmEmail(String confirmationToken);
}