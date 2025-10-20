package com.devsuperior.dscatalog.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class EmailDTO {

    @Email(message = "Invalid email")
    @NotBlank(message = "Field required")
    private String email;
    
}
