package com.javaProject.JavaProject.service.impl;



import com.javaProject.JavaProject.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendOtpEmail(String email, String otp) {
        // In a real application, integrate with an email service provider
        log.info("Sending OTP {} to email {}", otp, email);
        // Actual email sending implementation would go here
    }
}