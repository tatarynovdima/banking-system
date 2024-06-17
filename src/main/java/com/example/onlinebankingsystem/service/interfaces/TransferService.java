package com.example.onlinebankingsystem.service.interfaces;


import com.example.onlinebankingsystem.dto.TransferDto;
import org.springframework.http.ResponseEntity;

public interface TransferService {
    ResponseEntity<?> processTransfer(TransferDto transferDto, String email);
}
