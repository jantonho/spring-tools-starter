package com.carteira.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carteira.entidade.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
}
