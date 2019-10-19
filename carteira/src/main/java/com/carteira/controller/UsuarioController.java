package com.carteira.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carteira.dto.UsuarioDTO;
import com.carteira.entidade.Usuario;
import com.carteira.response.Response;
import com.carteira.servico.UsuarioServico;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired
	private UsuarioServico usuarioService;

	private ObjectMapper mapper = new ObjectMapper();

	@PostMapping
	public ResponseEntity<Response<UsuarioDTO>> create(@Valid @RequestBody UsuarioDTO dto, BindingResult result) {
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();
		
		Usuario usuario = usuarioService.save(mapper.convertValue(dto, Usuario.class));
		
		response.setData(mapper.convertValue(usuario, UsuarioDTO.class));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
