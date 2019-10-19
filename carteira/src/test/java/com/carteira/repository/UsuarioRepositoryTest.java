package com.carteira.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.carteira.entity.Usuario;
import com.carteira.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

	private static final String EMAIL = "teste@teste.com";
	@Autowired
	UsuarioRepository usuarioRep;
	
	@Before
	public void antes() {
		Usuario u = new Usuario();
		u.setNome("teste");
		u.setEmail(EMAIL);
		u.setSenha("123456");
		
		usuarioRep.save(u);
	}
	
	@After
	public void depois() {
		usuarioRep.deleteAll();
	}
	
	@Test
	public void salvar() {
		Usuario u = new Usuario();
		u.setNome("teste");
		u.setEmail(EMAIL);
		u.setSenha("123456");
		
		Usuario usuario = usuarioRep.save(u);
		
		assertNotNull(usuario);
	}
	
	@Test
	public void buscarPorEmail() {
		Optional<Usuario> usuario = usuarioRep.findByEmail("teste@teste.com");
		
		assertTrue(usuario.isPresent());
		assertEquals(usuario.get().getEmail(), EMAIL);
		
	}
}
