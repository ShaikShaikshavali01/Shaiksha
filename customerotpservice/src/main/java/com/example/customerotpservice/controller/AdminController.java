package com.example.customerotpservice.controller;

import com.example.customerotpservice.service.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final OtpService otpService;

    public AdminController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/unlock")
    public ResponseEntity<String> unlockUser(@RequestParam String email) {
        String response = otpService.unlockUser(email);
        return ResponseEntity.ok(response);
    }
}