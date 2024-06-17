package com.example.onlinebankingsystem.dto;

import com.example.onlinebankingsystem.entity.Card;
import com.example.onlinebankingsystem.entity.Client;
import com.example.onlinebankingsystem.entity.Country;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private CountryDto country;
    private CardDto cardDto;

    public ClientDto(Client client, Card card, Country country) {
        this.id = client.getId();
        this.email = client.getEmail();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.phoneNumber = client.getPhoneNumber();
        this.country = new CountryDto(country.getId(), country.getName());
        this.cardDto = new CardDto(card.getId(), card.getCardNumber(), card.getReleaseDate(), card.getBalance());
    }
}
