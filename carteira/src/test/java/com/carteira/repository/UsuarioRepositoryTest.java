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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.carteira.entity.TipoFuncao;
import com.carteira.entity.Usuario;
import com.carteira.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

	private static final String EMAIL = "test@test.com";
	@Autowired
	UsuarioRepository usuarioRep;
	
	@Before
	public void antes() {
		Usuario u = new Usuario();
		u.setNome("test");
		u.setEmail(EMAIL);
		u.setSenha("12345678");
		u.setFuncao(TipoFuncao.ROLE_USER);
		
		usuarioRep.save(u);
	}
	
	@After
	public void depois() {
		usuarioRep.deleteAll();
	}
	
	@Test
	public void salvar() {
		Usuario u = new Usuario();
		u.setNome("test");
		u.setEmail(EMAIL);
		u.setSenha("12345678");
		u.setFuncao(TipoFuncao.ROLE_USER);
		
		Usuario usuario = usuarioRep.save(u);
		
		assertNotNull(usuario);
	}
	
	@Test
	public void buscarPorEmail() {
		Optional<Usuario> usuario = usuarioRep.findByEmail(EMAIL);
		
		assertTrue(usuario.isPresent());
		assertEquals(usuario.get().getEmail(), EMAIL);
		
	}
}
