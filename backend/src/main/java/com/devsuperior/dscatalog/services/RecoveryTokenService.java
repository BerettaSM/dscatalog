package com.devsuperior.dscatalog.services;

import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class RecoveryTokenService {

    public String generateToken() {
        String uuid = UUID.randomUUID().toString();
        return Base64.getEncoder().encodeToString(uuid.getBytes());
    }

}
