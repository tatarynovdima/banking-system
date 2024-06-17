package com.example.onlinebankingsystem.utils;

import com.example.onlinebankingsystem.entity.Country;
import com.example.onlinebankingsystem.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class CountryInitialize {
    @Autowired
    private CountryRepository countryRepository;

    @PostConstruct
    public void initCountries() {
        List<String> euCountries = Arrays.asList("Austria", "Belgium", "Bulgaria", "Croatia",
                "Cyprus", "Czech Republic", "Denmark", "Estonia",
                "Finland", "France", "Germany", "Greece",
                "Hungary", "Ireland", "Italy", "Latvia",
                "Lithuania", "Luxembourg", "Malta", "Netherlands",
                "Poland", "Portugal", "Romania", "Slovakia",
                "Slovenia", "Spain", "Sweden");

        for (String countryName : euCountries) {
            if (!countryRepository.existsByName(countryName)) {
                Country country = new Country();
                country.setName(countryName);
                countryRepository.save(country);
            }
        }
    }
}