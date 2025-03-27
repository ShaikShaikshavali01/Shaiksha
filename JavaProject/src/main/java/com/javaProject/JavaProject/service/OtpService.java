package com.javaProject.JavaProject.service;



import com.javaProject.JavaProject.dto.OtpRequest;
import com.javaProject.JavaProject.dto.OtpResponse;
import com.javaProject.JavaProject.dto.OtpValidationRequest;

public interface OtpService {
    OtpResponse sendOtp(OtpRequest otpRequest);
    OtpResponse validateOtp(OtpValidationRequest otpValidationRequest);
    void unlockUser(String email);
}
