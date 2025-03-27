package com.javaProject.JavaProject.controller;



import com.javaProject.JavaProject.dto.OtpRequest;
import com.javaProject.JavaProject.dto.OtpResponse;
import com.javaProject.JavaProject.dto.OtpValidationRequest;
import com.javaProject.JavaProject.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController {
    private final OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<OtpResponse> sendOtp(@Valid @RequestBody OtpRequest otpRequest) {
        OtpResponse response = otpService.sendOtp(otpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<OtpResponse> validateOtp(@Valid @RequestBody OtpValidationRequest otpValidationRequest) {
        OtpResponse response = otpService.validateOtp(otpValidationRequest);
        return ResponseEntity.ok(response);
    }
}