package com.example.onlinebankingsystem.service.interfaces;

import com.example.onlinebankingsystem.dto.ReplenishmentDto;
import org.springframework.http.ResponseEntity;

public interface ReplenishmentService {
    ResponseEntity<?> processReplenishment(ReplenishmentDto replenishmentDto, String email);
}
