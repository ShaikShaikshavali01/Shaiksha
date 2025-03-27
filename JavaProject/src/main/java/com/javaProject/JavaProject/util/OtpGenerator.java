package com.javaProject.JavaProject.util;



import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpGenerator {
    private static final int OTP_LENGTH = 6;

    public String generateOtp() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }
}