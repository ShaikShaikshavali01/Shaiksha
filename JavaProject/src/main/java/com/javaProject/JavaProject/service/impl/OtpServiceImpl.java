package com.javaProject.JavaProject.service.impl;



import com.javaProject.JavaProject.dto.OtpRequest;
import com.javaProject.JavaProject.dto.OtpResponse;
import com.javaProject.JavaProject.dto.OtpValidationRequest;
import com.javaProject.JavaProject.exception.CustomerLockedException;
import com.javaProject.JavaProject.exception.CustomerNotFoundException;
import com.javaProject.JavaProject.model.OtpEntity;
import com.javaProject.JavaProject.repository.CustomerRepository;
import com.javaProject.JavaProject.repository.OtpRepository;
import com.javaProject.JavaProject.service.EmailService;
import com.javaProject.JavaProject.service.OtpService;
import com.javaProject.JavaProject.util.EncryptionUtil;
import com.javaProject.JavaProject.util.OtpGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OtpRepository otpRepository;
    private final CustomerRepository customerRepository;
    private final OtpGenerator otpGenerator;
    private final EncryptionUtil encryptionUtil;
    private final EmailService emailService;

    @Override
    @Transactional
    public OtpResponse sendOtp(OtpRequest otpRequest) {
        String email = otpRequest.getEmail();

        // Check if customer exists
        if (!customerRepository.existsByEmail(email)) {
            throw new CustomerNotFoundException("Customer with email " + email + " not found");
        }

        // Check if user is locked
        Optional<OtpEntity> existingOtp = otpRepository.findByEmail(email);
        if (existingOtp.isPresent() && existingOtp.get().isLocked()) {
            throw new CustomerLockedException("User is locked. Please contact support.");
        }

        // Generate OTP
        String otp = otpGenerator.generateOtp();
        String encryptedOtp = encryptionUtil.encrypt(otp);

        // Save or update OTP record
        OtpEntity otpEntity = existingOtp.orElse(new OtpEntity());
        otpEntity.setEmail(email);
        otpEntity.setOtp(encryptedOtp);
        otpEntity.setRetryCount(0);
        otpEntity.setLocked(false);
        otpRepository.save(otpEntity);

        // Send OTP via email
        emailService.sendOtpEmail(email, otp);

        return new OtpResponse(email, "OTP sent successfully", true);
    }

    @Override
    @Transactional
    public OtpResponse validateOtp(OtpValidationRequest otpValidationRequest) {
        String email = otpValidationRequest.getEmail();
        String userOtp = otpValidationRequest.getOtp();

        OtpEntity otpEntity = otpRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("OTP not found for email " + email));

        if (otpEntity.isLocked()) {
            throw new CustomerLockedException("User is locked. Please contact support.");
        }

        String decryptedOtp = encryptionUtil.decrypt(otpEntity.getOtp());

        if (decryptedOtp.equals(userOtp)) {
            otpRepository.delete(otpEntity);
            return new OtpResponse(email, "OTP validated successfully", true);
        } else {
            otpEntity.setRetryCount(otpEntity.getRetryCount() + 1);
            if (otpEntity.getRetryCount() >= 3) {
                otpEntity.setLocked(true);
            }
            otpRepository.save(otpEntity);

            String message = otpEntity.isLocked() ?
                    "Invalid OTP. User locked due to too many attempts. Please contact support." :
                    "Invalid OTP. Attempts remaining: " + (3 - otpEntity.getRetryCount());

            return new OtpResponse(email, message, false);
        }
    }

    @Override
    @Transactional
    public void unlockUser(String email) {
        otpRepository.deleteByEmail(email);
    }
}
