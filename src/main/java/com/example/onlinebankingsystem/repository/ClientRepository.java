package com.example.onlinebankingsystem.repository;

import com.example.onlinebankingsystem.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String username);
    Optional<Client> findByPhoneNumber(String phoneNumber);
    Boolean existsByEmail(String email);
}
