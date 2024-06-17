package com.example.onlinebankingsystem.service.interfaces;

import com.example.onlinebankingsystem.entity.Card;

import java.util.Optional;

public interface CardService {
    void saveCard(Card card);

    Optional<Card> findCardByCardNumber(String recipientCardNumber);
}
