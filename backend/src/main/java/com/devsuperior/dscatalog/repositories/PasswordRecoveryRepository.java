package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscatalog.domain.entities.PasswordRecovery;

public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecovery, Long>{
}
