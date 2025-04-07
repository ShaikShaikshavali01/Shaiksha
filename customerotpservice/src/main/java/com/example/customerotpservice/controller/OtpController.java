package com.example.customerotpservice.controller;

import com.example.customerotpservice.dto.OtpRequestDto;
import com.example.customerotpservice.dto.OtpResponseDto;
import com.example.customerotpservice.dto.OtpValidationDto;
import com.example.customerotpservice.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController {
    private final OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<OtpResponseDto> sendOtp(@Valid @RequestBody OtpRequestDto otpRequest) {
        OtpResponseDto response = otpService.generateAndSendOtp(otpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateOtp(@Valid @RequestBody OtpValidationDto otpValidation) {
        String response = otpService.validateOtp(otpValidation);
        return ResponseEntity.ok(response);
    }
}