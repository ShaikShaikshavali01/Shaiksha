package com.example.customerotpservice.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {
    private final StandardPBEStringEncryptor encryptor;

    public EncryptionUtil(@Value("${jasypt.encryptor.password}") String password) {
        this.encryptor = new StandardPBEStringEncryptor();
        this.encryptor.setPassword(password);
        this.encryptor.setAlgorithm("PBEWithMD5AndDES");
    }

    public String encrypt(String data) {
        return encryptor.encrypt(data);
    }

    public String decrypt(String encryptedData) {
        return encryptor.decrypt(encryptedData);
    }
}