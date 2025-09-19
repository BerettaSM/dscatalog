package com.devsuperior.dscatalog.domain.dto;

import com.devsuperior.dscatalog.services.validation.UserInsertValid;

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

    private String password;

}
