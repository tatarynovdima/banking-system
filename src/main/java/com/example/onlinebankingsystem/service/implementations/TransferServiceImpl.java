package com.example.onlinebankingsystem.service.implementations;

import com.example.onlinebankingsystem.dto.TransferDto;
import com.example.onlinebankingsystem.entity.Card;
import com.example.onlinebankingsystem.entity.Client;
import com.example.onlinebankingsystem.exceptions.AppError;
import com.example.onlinebankingsystem.service.interfaces.CardService;
import com.example.onlinebankingsystem.service.interfaces.ClientService;
import com.example.onlinebankingsystem.service.interfaces.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final ClientService clientService;
    private final CardService cardService;

    @Override
    public ResponseEntity<?> processTransfer(TransferDto transferDto, String email) {
        Optional<Client> clientOptional = clientService.findByClientEmail(email);

        if (!clientOptional.isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Client not found"), HttpStatus.BAD_REQUEST);
        }
        Client client = clientOptional.get();

        Card senderCard = client.getCard();
        if (senderCard.getCardNumber().equals(transferDto.getRecipientCardNumber())) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "This is your card, you cannot transfer funds from your card to your card"), HttpStatus.BAD_REQUEST);
        }

        if (senderCard.getBalance().compareTo(transferDto.getAmount()) < 0) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "The amount of funds being transferred exceeds your balance"), HttpStatus.BAD_REQUEST);
        }

        Optional<Card> recipientCardOptional = cardService.findCardByCardNumber(transferDto.getRecipientCardNumber());
        if (!recipientCardOptional.isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Such card is not registered in the system"), HttpStatus.BAD_REQUEST);
        }

        BigDecimal newSenderBalance = senderCard.getBalance().subtract(transferDto.getAmount());
        senderCard.setBalance(newSenderBalance);
        cardService.saveCard(senderCard);

        Card recipientCard = recipientCardOptional.get();

        BigDecimal newRecipientBalance = recipientCard.getBalance().add(transferDto.getAmount());
        recipientCard.setBalance(newRecipientBalance);
        cardService.saveCard(recipientCard);

        return ResponseEntity.ok().body(Map.of("message", "Transfer successful"));
    }

}
