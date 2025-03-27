package com.javaProject.JavaProject.dto;



import lombok.Data;

@Data
public class OtpResponse {
    private String email;
    private String message;
    private boolean success;

    public OtpResponse(String email, String otpSentSuccessfully, boolean b) {
    }
}
