package com.example.onlinebankingsystem.service.implementations;

import com.example.onlinebankingsystem.entity.Card;
import com.example.onlinebankingsystem.repository.CardRepository;
import com.example.onlinebankingsystem.service.interfaces.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Optional<Card> findCardByCardNumber(String recipientCardNumber) {
        return cardRepository.findByCardNumber(recipientCardNumber);
    }
}
