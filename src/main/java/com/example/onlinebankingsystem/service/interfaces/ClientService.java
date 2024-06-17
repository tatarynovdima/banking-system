package com.example.onlinebankingsystem.service.interfaces;

import com.example.onlinebankingsystem.dto.FullNameBalanceDTO;
import com.example.onlinebankingsystem.dto.RegistrationClientDto;
import com.example.onlinebankingsystem.entity.Client;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface ClientService extends UserDetailsService {
    Optional<Client> findByClientEmail(String email);

    Optional<Client> findByPhoneNumber(String phoneNumber);

    Client createNewUser(RegistrationClientDto registrationUserDto);

    FullNameBalanceDTO getFullNameBalanceByEmail(String email);
}
