package com.devsuperior.dscatalog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.domain.dto.EmailDTO;
import com.devsuperior.dscatalog.domain.dto.PasswordRecoveryDTO;
import com.devsuperior.dscatalog.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/recover-token")
    public ResponseEntity<String> createRecoverToken(@Valid @RequestBody EmailDTO dto) {
        authService.createRecoverToken(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/new-password")
    public ResponseEntity<Void> recoverPassword(@Valid @RequestBody PasswordRecoveryDTO dto) {
        authService.recoverPassword(dto);
        return ResponseEntity.noContent().build();
    }
    
}
