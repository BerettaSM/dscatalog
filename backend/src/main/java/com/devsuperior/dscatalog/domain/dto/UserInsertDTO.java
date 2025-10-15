package com.devsuperior.dscatalog.domain.dto;

import com.devsuperior.dscatalog.services.validation.UserInsertValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@UserInsertValid
public class UserInsertDTO extends UserDTO {

    @NotBlank(message = "Field required")
    @Size(min = 8, message = "Should have at least 8 characters")
    private String password;

}
