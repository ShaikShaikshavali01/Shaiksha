package com.javaProject.JavaProject.controller;



import com.javaProject.JavaProject.dto.UnlockRequest;
import com.javaProject.JavaProject.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final OtpService otpService;

    @PostMapping("/unlock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> unlockUser(@Valid @RequestBody UnlockRequest unlockRequest) {
        otpService.unlockUser(unlockRequest.getEmail());
        return ResponseEntity.ok("User unlocked successfully");
    }
}
