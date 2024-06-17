package com.example.onlinebankingsystem.service.implementations;

import com.example.onlinebankingsystem.dto.FullNameBalanceDTO;
import com.example.onlinebankingsystem.dto.RegistrationClientDto;
import com.example.onlinebankingsystem.entity.*;
import com.example.onlinebankingsystem.exceptions.ResourceNotFoundException;
import com.example.onlinebankingsystem.repository.CardRepository;
import com.example.onlinebankingsystem.repository.ClientRepository;
import com.example.onlinebankingsystem.repository.ConfirmationTokenRepository;
import com.example.onlinebankingsystem.repository.CountryRepository;
import com.example.onlinebankingsystem.service.interfaces.ClientService;
import com.example.onlinebankingsystem.service.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements UserDetailsService, ClientService {
    private final ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Client> findByClientEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Optional<Client> findByPhoneNumber(String phoneNumber) {
        return clientRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = this.findByClientEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Client with email: '%s' not found", email)
        ));
        return new org.springframework.security.core.userdetails.User(
                client.getEmail(),
                client.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(client.getRole().name()))
        );
    }

    @Override
    public Client createNewUser(RegistrationClientDto registrationUserDto) {
        Client client = new Client();
        client.setEmail(registrationUserDto.getEmail());
        client.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        client.setFirstName(registrationUserDto.getFirstName());
        client.setLastName(registrationUserDto.getLastName());
        client.setPhoneNumber(registrationUserDto.getPhoneNumber());
        client.setRole(Role.ROLE_CLIENT);

        Country country = countryRepository.findByName(registrationUserDto.getCountryName())
                .orElseThrow(() -> new RuntimeException("Country not found"));
        client.setCountry(country);

        Card card = new Card();
        card.setCardNumber(card.generateCardNumber());
        card.setReleaseDate(LocalDate.now());
        card.setBalance(BigDecimal.ZERO);
        client.setCard(card);

        cardRepository.save(card);

        clientRepository.save(client);

        ConfirmationToken confirmationToken = new ConfirmationToken(client);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(client.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here: "
                +"http://localhost:5173/confirm-account?token="+confirmationToken.getConfirmationToken());
        emailService.sendEmail(mailMessage);

        return client;
    }

    @Override
    public FullNameBalanceDTO getFullNameBalanceByEmail(String email) {
        Client client = findByClientEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with email: " + email));

        Card card = client.getCard();
        return new FullNameBalanceDTO(client.getFirstName(), client.getLastName(), card.getBalance());
    }

}
