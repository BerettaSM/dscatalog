package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscatalog.domain.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

    Optional<Role> findByAuthority(String authority);

}
