package com.example.customerotpservice.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendOtpEmail(String email, int otp) {
        // In a real application, this would send an actual email
        System.out.println("Sending OTP " + otp + " to email: " + email);
        // For demo purposes, we're just printing to console
    }
}