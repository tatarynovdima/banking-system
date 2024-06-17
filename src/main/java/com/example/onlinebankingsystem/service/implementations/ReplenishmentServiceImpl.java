package com.example.onlinebankingsystem.service.implementations;

import com.example.onlinebankingsystem.dto.ReplenishmentDto;
import com.example.onlinebankingsystem.entity.Card;
import com.example.onlinebankingsystem.entity.Client;
import com.example.onlinebankingsystem.exceptions.AppError;
import com.example.onlinebankingsystem.service.interfaces.CardService;
import com.example.onlinebankingsystem.service.interfaces.ClientService;
import com.example.onlinebankingsystem.service.interfaces.ReplenishmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplenishmentServiceImpl implements ReplenishmentService {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CardService cardService;

    @Override
    public ResponseEntity<?> processReplenishment(ReplenishmentDto replenishmentDto, String email) {
        Optional<Client> clientOptional = clientService.findByClientEmail(email);

        if (!clientOptional.isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Client not found"), HttpStatus.BAD_REQUEST);
        }
        Client client = clientOptional.get();

        Card recipientCard = client.getCard();

        BigDecimal newRecipientBalance = recipientCard.getBalance().add(replenishmentDto.getAmount());
        recipientCard.setBalance(newRecipientBalance);
        cardService.saveCard(recipientCard);

        return ResponseEntity.ok().body(Map.of("message", "Replenishment successful"));
    }
}
