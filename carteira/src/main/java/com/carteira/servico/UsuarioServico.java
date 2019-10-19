package com.carteira.servico;

import java.util.Optional;

import com.carteira.entidade.Usuario;

public interface UsuarioServico {

	Usuario save(Usuario u);
	
	Optional<Usuario> findByEmail(String email);
}
