package com.devsuperior.dscatalog.domain.dto;

import java.util.HashSet;
import java.util.Set;

import com.devsuperior.dscatalog.domain.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "Field required")
    private String firstName;
    private String lastName;

    @Email(message = "Enter a valid email")
    private String email;

    private final Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(User entity) {
        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        email = entity.getEmail();
        addRoles(entity.getRoles()
                .stream()
                .map(RoleDTO::from)
                .toArray(RoleDTO[]::new));
    }

    public final void addRoles(RoleDTO... roles) {
        this.roles.addAll(Set.of(roles));
    }

    public static UserDTO from(User entity) {
        return new UserDTO(entity);
    }

}
