package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscatalog.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
