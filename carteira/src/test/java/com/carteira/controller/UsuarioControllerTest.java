package com.carteira.controller;

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

import com.carteira.entidade.Usuario;
import com.carteira.servico.UsuarioServico;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {

	@MockBean
	UsuarioServico usuarioService;

	@Autowired
	MockMvc mvc;

	@Test
	public void save() throws Exception {
		BDDMockito.given(usuarioService.save(Mockito.any(Usuario.class))).willReturn(getMockUsuario());
		
		mvc.perform(MockMvcRequestBuilders.post("/usuario").content(getMockUsuarioJson())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}

	public Usuario getMockUsuario() {
		Usuario u = new Usuario();
		u.setNome("teste");
		u.setEmail("teste@teste.com");
		u.setSenha("123456");

		return u;

	}

	public String getMockUsuarioJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(getMockUsuario());
	}
}
