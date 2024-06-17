package com.example.onlinebankingsystem.service.implementations;

import com.example.onlinebankingsystem.dto.ClientDto;
import com.example.onlinebankingsystem.dto.JwtRequest;
import com.example.onlinebankingsystem.dto.JwtResponse;
import com.example.onlinebankingsystem.dto.RegistrationClientDto;
import com.example.onlinebankingsystem.entity.Client;
import com.example.onlinebankingsystem.entity.ConfirmationToken;
import com.example.onlinebankingsystem.exceptions.AppError;
import com.example.onlinebankingsystem.repository.ClientRepository;
import com.example.onlinebankingsystem.repository.ConfirmationTokenRepository;
import com.example.onlinebankingsystem.service.interfaces.AuthService;
import com.example.onlinebankingsystem.service.interfaces.ClientService;
import com.example.onlinebankingsystem.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ClientService clientService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final ClientRepository clientRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public ResponseEntity<?> createAuthToken(JwtRequest authRequest) {
        UserDetails userDetails = clientService.loadUserByUsername(authRequest.getEmail());
        if (userDetails == null) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "User not found"), HttpStatus.BAD_REQUEST);
        }

        Optional<Client> client = clientService.findByClientEmail(authRequest.getEmail());
        if (client.isPresent() && !client.get().isEnabled()) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Account is not activated"), HttpStatus.BAD_REQUEST);
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Please check your password and email and try again"), HttpStatus.BAD_REQUEST);
        }
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }



    @Override
    public ResponseEntity<?> createNewUser(RegistrationClientDto registrationUserDto) {
        try {
            if (clientService.findByClientEmail(registrationUserDto.getEmail()).isPresent()) {
                return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "User with specified email already exists"), HttpStatus.BAD_REQUEST);
            }
            if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
                return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Passwords do not match"), HttpStatus.BAD_REQUEST);
            }

            Client client = clientService.createNewUser(registrationUserDto);
            return ResponseEntity.ok(new ClientDto(client, client.getCard(), client.getCountry()));
        } catch (Exception ex) {
            return new ResponseEntity<>(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Database error while creating user"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null && !token.isUsed()) {
            Optional<Client> client = clientService.findByClientEmail(token.getClient().getEmail());
            client.ifPresent(c -> {
                c.setEnabled(true);
                clientRepository.save(c);
            });

            // Пометить токен как использованный
            token.setUsed(true);
            confirmationTokenRepository.save(token);

            return ResponseEntity.ok("Email verified successfully!");
        }

        return ResponseEntity.badRequest().body("Error: Email is already verified");
    }

}