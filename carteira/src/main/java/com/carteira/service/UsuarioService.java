package com.carteira.service;

import java.util.Optional;

import com.carteira.entity.Usuario;

public interface UsuarioService {

	Usuario save(Usuario u);
	
	Optional<Usuario> findByEmail(String email);
}
