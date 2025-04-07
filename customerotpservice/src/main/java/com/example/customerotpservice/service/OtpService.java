package com.example.customerotpservice.service;

import com.example.customerotpservice.dto.OtpRequestDto;
import com.example.customerotpservice.dto.OtpResponseDto;
import com.example.customerotpservice.dto.OtpValidationDto;
import com.example.customerotpservice.exception.InvalidOtpException;
import com.example.customerotpservice.exception.LockedUserException;
import com.example.customerotpservice.exception.ResourceNotFoundException;
import com.example.customerotpservice.model.Otp;
import com.example.customerotpservice.repository.CustomerRepository;
import com.example.customerotpservice.repository.OtpRepository;
import com.example.customerotpservice.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository otpRepository;
    private final CustomerRepository customerRepository;
    private final EmailService emailService;
    private final EncryptionUtil encryptionUtil;

    public OtpResponseDto generateAndSendOtp(OtpRequestDto otpRequest) {
        // Check if user is locked
        Optional<Otp> existingOtp = otpRepository.findByEmail(otpRequest.getEmail());
        if (existingOtp.isPresent() && existingOtp.get().isLocked()) {
            throw new LockedUserException("User is locked. Please contact admin.");
        }

        // Check if customer exists
        if (!customerRepository.existsByEmail(otpRequest.getEmail())) {
            throw new ResourceNotFoundException("Customer not found with email: " + otpRequest.getEmail());
        }

        // Generate OTP
        int otpValue = new SecureRandom().nextInt(900000) + 100000; // 6-digit OTP
        String encryptedOtp = encryptionUtil.encrypt(String.valueOf(otpValue));

        // Save or update OTP record
        Otp otp = existingOtp.orElse(new Otp());
        otp.setEmail(otpRequest.getEmail());
        otp.setOtp(encryptedOtp);
        otp.setRetryCount(0);
        otp.setLocked(false);
        otpRepository.save(otp);

        // Send OTP (in a real application, this would send an actual email)
        emailService.sendOtpEmail(otpRequest.getEmail(), otpValue);

        return new OtpResponseDto(otpRequest.getEmail(), "OTP sent successfully", otpValue);
    }

    @Transactional
    public String validateOtp(OtpValidationDto otpValidation) {
        Otp otp = otpRepository.findByEmail(otpValidation.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("OTP not found for email: " + otpValidation.getEmail()));

        if (otp.isLocked()) {
            throw new LockedUserException("User is locked. Please contact admin.");
        }

        String decryptedOtp = encryptionUtil.decrypt(otp.getOtp());
        if (decryptedOtp.equals(otpValidation.getOtp())) {
            otpRepository.delete(otp);
            return "OTP validated successfully";
        } else {
            otp.setRetryCount(otp.getRetryCount() + 1);
            if (otp.getRetryCount() >= 3) {
                otp.setLocked(true);
            }
            otpRepository.save(otp);
            throw new InvalidOtpException("Invalid OTP. Attempts left: " + (3 - otp.getRetryCount()));
        }
    }

    @Transactional
    public String unlockUser(String email) {
        otpRepository.deleteByEmail(email);
        return "User unlocked successfully";
    }
}