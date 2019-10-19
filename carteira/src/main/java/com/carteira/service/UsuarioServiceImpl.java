package com.carteira.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
