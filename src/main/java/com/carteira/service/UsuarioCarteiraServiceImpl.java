package com.carteira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carteira.entity.UsuarioCarteira;
import com.carteira.repository.UsuarioCarteiraRepository;

@Service
public class UsuarioCarteiraServiceImpl implements UsuarioCarteiraService {

	@Autowired
	UsuarioCarteiraRepository usuarioCarteiraRep;

	@Override
	public UsuarioCarteira save(UsuarioCarteira uc) {
		return usuarioCarteiraRep.save(uc);
	}
}
