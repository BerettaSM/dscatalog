package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dscatalog.domain.entities.PasswordRecovery;

public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecovery, Long>{

    @Query(value = """
        FROM PasswordRecovery p
        WHERE p.token = :token
            AND p.expiration > CURRENT_TIMESTAMP
    """)
    Optional<PasswordRecovery> findValidPasswordRecovery(String token);

}
