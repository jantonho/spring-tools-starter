package com.carteira.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carteira.entity.Usuario;
import com.carteira.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioRepository usuarioRep;

	@Override
	public Usuario save(Usuario u) {
		return usuarioRep.save(u);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		return usuarioRep.findByEmail(email);
	}
}
