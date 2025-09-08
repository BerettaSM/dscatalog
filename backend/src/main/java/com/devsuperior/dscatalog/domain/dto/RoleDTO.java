package com.devsuperior.dscatalog.domain.dto;

import com.devsuperior.dscatalog.domain.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RoleDTO {
    
    private Long id;
    private String authority;

    public RoleDTO(Role entity) {
        id = entity.getId();
        authority = entity.getAuthority();
    }

    public static RoleDTO from(Role entity) {
        return new RoleDTO(entity);
    }
    
}
