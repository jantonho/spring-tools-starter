package com.carteira.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.carteira.entity.Usuario;
import com.carteira.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {

	private static final Long ID = 1L;
	private static final String NOME = "teste";
	private static final String EMAIL = "teste@teste.com";
	private static final String SENHA = "QWERTY";
	
	@MockBean
	UsuarioService usuarioService;

	@Autowired
	MockMvc mvc;

	@Test
	public void salvar() throws Exception {
		BDDMockito.given(usuarioService.save(Mockito.any(Usuario.class))).willReturn(getMockUsuario(ID, NOME, EMAIL, SENHA));
		
		mvc.perform(MockMvcRequestBuilders.post("/usuario").content(getMockUsuarioJson(ID, NOME, EMAIL, SENHA))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.senha").doesNotExist());
	}
	
	@Test
	public void salvarUsuarioInvalido() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/usuario").content(getMockUsuarioJson(ID, NOME, "EMAIL_ERRADO", SENHA))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors[0]").value("Email inválido"));
	}
	

	public Usuario getMockUsuario(Long id, String nome, String email, String senha) {
		Usuario u = new Usuario();
		u.setId(id);
		u.setNome(nome);
		u.setEmail(email);
		u.setSenha(senha);

		return u;
	}

	public String getMockUsuarioJson(Long id, String nome, String email, String senha) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(getMockUsuario(id, nome, email, senha));
	}
}
