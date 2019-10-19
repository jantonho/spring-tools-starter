package com.carteira.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carteira.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
}
