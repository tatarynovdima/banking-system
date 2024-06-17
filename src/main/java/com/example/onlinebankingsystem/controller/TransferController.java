package com.example.onlinebankingsystem.controller;

import com.example.onlinebankingsystem.dto.TransferDto;
import com.example.onlinebankingsystem.service.interfaces.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<?> createNewTransfer(Principal principal, @RequestBody TransferDto transferDto) {
        String email = principal.getName();
        return transferService.processTransfer(transferDto, email);
    }
}