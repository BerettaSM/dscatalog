package com.devsuperior.dscatalog.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.domain.dto.EmailDTO;
import com.devsuperior.dscatalog.domain.dto.PasswordRecoveryDTO;
import com.devsuperior.dscatalog.domain.entities.PasswordRecovery;
import com.devsuperior.dscatalog.domain.entities.User;
import com.devsuperior.dscatalog.domain.models.EmailModel;
import com.devsuperior.dscatalog.repositories.PasswordRecoveryRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.services.exceptions.InvalidTokenException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final UserRepository userRepository;

    private final EmailService emailService;
    private final RecoveryTokenService passwordRecoveryService;

    private final PasswordEncoder passwordEncoder;

    @Value("${email.password-recovery.token.minutes}")
    private Integer tokenValidityInMinutes;

    @Value("${email.password-recovery.uri}")
    private String recoveryEndpoint;

    @Transactional
    public void createRecoverToken(EmailDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("Email not registered"));
        
        String token = passwordRecoveryService.generateToken();
        Instant expiration = Instant.now().plusSeconds(tokenValidityInMinutes * 60);
        PasswordRecovery recovery = new PasswordRecovery(null, token, user.getEmail(), expiration);

        recovery = passwordRecoveryRepository.save(recovery);

        String body = """
            Acess the following link to re-define your password:
            
            %s/%s

            The link will expire in %d minutes.
            """.formatted(recoveryEndpoint, token, tokenValidityInMinutes);

        EmailModel email = EmailModel.of(user.getEmail(), "Password recovery", body);
        emailService.sendEmail(email);
    }

    @Transactional
    public void recoverPassword(PasswordRecoveryDTO dto) {
        PasswordRecovery recovery = passwordRecoveryRepository
            .findValidPasswordRecovery(dto.getToken())
            .orElseThrow(() -> new InvalidTokenException());
        User user = userRepository.findByEmail(recovery.getEmail())
            .orElseThrow(() -> new InvalidTokenException());
        
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
        passwordRecoveryRepository.delete(recovery);
    }
    
}
