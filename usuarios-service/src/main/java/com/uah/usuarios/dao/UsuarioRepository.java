package com.uah.usuarios.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.uah.usuarios.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    boolean existsByUsername(String username);
    Optional<UsuarioEntity> findByUsername(String username);
}