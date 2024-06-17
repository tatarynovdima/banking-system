package com.example.onlinebankingsystem.controller;

import com.example.onlinebankingsystem.dto.CountryDto;
import com.example.onlinebankingsystem.dto.mapper.CountryMapper;
import com.example.onlinebankingsystem.entity.Country;
import com.example.onlinebankingsystem.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CountryController {
    @Autowired
    private CountryRepository countryRepository;

    @GetMapping("/countries")
    public List<CountryDto> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        return CountryMapper.INSTANCE.countriesToCountryDtos(countries);
    }
}
