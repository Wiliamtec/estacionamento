package com.estacionamento_api.repository;

import com.estacionamento_api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByUsername(String username);

    @Query("select u.role from usuarios u where u.username like :username")
    Usuario.Role findRoleByUserName(String username);
}
