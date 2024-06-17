package com.example.onlinebankingsystem.controller;

import com.example.onlinebankingsystem.dto.FullNameBalanceDTO;
import com.example.onlinebankingsystem.service.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class MainController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/balance")
    public ResponseEntity<FullNameBalanceDTO> getClientBalance(Principal principal) {
        String email = principal.getName();
        FullNameBalanceDTO fullNameBalanceDTO = clientService.getFullNameBalanceByEmail(email);
        return ResponseEntity.ok(fullNameBalanceDTO);
    }
}