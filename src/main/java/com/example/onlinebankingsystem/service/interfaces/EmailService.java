package com.example.onlinebankingsystem.service.interfaces;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
     void sendEmail(SimpleMailMessage email);
}