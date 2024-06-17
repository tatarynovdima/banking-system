package com.example.onlinebankingsystem.controller;

import com.example.onlinebankingsystem.dto.ReplenishmentDto;
import com.example.onlinebankingsystem.service.interfaces.ReplenishmentService;
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
public class ReplenishmentController {
    private final ReplenishmentService replenishmentService;
    @PostMapping("/replenishment")
    public ResponseEntity<?> createNewReplenishment(Principal principal, @RequestBody ReplenishmentDto replenishmentDto) {
        String email = principal.getName();
        return replenishmentService.processReplenishment(replenishmentDto, email);
    }
}
