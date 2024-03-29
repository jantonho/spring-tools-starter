package com.carteira.service;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.carteira.entity.Usuario;
import com.carteira.repository.UsuarioRepository;
import com.carteira.service.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@MockBean
	UsuarioRepository usuarioRep;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Before
	public void antes() {
		BDDMockito.given(usuarioRep.findByEmail(Mockito.anyString())).willReturn(Optional.of(new Usuario()));
	}

	@Test
	public void buscarPorEmail() {
		Optional<Usuario> usuario = usuarioService.findByEmail("test@test.com");
		
		assertTrue(usuario.isPresent());
	}
	
}
