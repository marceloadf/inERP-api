package com.inerpapi.repository;

import java.util.Optional;

import com.inerpapi.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByEmail(String email);
}