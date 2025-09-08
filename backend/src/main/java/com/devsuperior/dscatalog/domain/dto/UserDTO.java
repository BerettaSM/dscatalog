package com.devsuperior.dscatalog.domain.dto;

import java.util.HashSet;
import java.util.Set;

import com.devsuperior.dscatalog.domain.entities.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private final Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(User entity) {
        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        email = entity.getEmail();
        password = null;
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
