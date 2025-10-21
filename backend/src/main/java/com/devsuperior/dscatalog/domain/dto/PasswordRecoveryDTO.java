package com.devsuperior.dscatalog.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PasswordRecoveryDTO {

    @NotBlank(message = "Should not be blank")
    private String token;

    @Size(min = 8, message = "Should have at least 8 characters")
    @NotBlank(message = "Should not be blank")
    private String password;
    
}
