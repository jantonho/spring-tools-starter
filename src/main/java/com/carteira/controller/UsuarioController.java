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
import com.carteira.entity.Usuario;
import com.carteira.response.Response;
import com.carteira.service.UsuarioService;
import com.carteira.util.Bcrypt;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	private ObjectMapper mapper = new ObjectMapper();

	@PostMapping
	public ResponseEntity<Response<UsuarioDTO>> create(@Valid @RequestBody UsuarioDTO dto, BindingResult result) {
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		// criptografando senha
		dto.setSenha(Bcrypt.getHash(dto.getSenha()));
		
		Usuario usuario = usuarioService.save(mapper.convertValue(dto, Usuario.class));
		
		// omitindo senha do retorno
		usuario.setSenha(null);
		
		response.setData(mapper.convertValue(usuario, UsuarioDTO.class));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}