package com.example.onlinebankingsystem.dto.mapper;

import com.example.onlinebankingsystem.dto.CountryDto;
import com.example.onlinebankingsystem.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CountryMapper {
    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    CountryDto countryToCountryDto(Country country);

    List<CountryDto> countriesToCountryDtos(List<Country> countries);
}