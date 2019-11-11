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

import com.carteira.dto.UsuarioCarteiraDTO;
import com.carteira.entity.Carteira;
import com.carteira.entity.Usuario;
import com.carteira.entity.UsuarioCarteira;
import com.carteira.response.Response;
import com.carteira.service.UsuarioCarteiraService;

@RestController
@RequestMapping("usuario-carteira")
public class UsuarioCarteiraController {

	@Autowired
	private UsuarioCarteiraService usuarioCarteiraService;

	@PostMapping
	public ResponseEntity<Response<UsuarioCarteiraDTO>> create(@Valid @RequestBody UsuarioCarteiraDTO dto, BindingResult result) {
		Response<UsuarioCarteiraDTO> response = new Response<UsuarioCarteiraDTO>();
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		UsuarioCarteira usuarioCarteira = usuarioCarteiraService.save(dto2Entity(dto));
		
		response.setData(entity2Dto(usuarioCarteira));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	public UsuarioCarteira dto2Entity(UsuarioCarteiraDTO dto) {
		UsuarioCarteira uc = new UsuarioCarteira();
		
		Carteira c = new Carteira();
		c.setId(dto.getIdCarteira());
		uc.setCarteira(c);
		
		Usuario u = new Usuario();
		u.setId(dto.getIdUsuario());
		uc.setUsuario(u);
		
		return uc;
	}


	public UsuarioCarteiraDTO entity2Dto(UsuarioCarteira entity) {
		UsuarioCarteiraDTO dto = new UsuarioCarteiraDTO();
		
		dto.setId(entity.getId());
		dto.setIdCarteira(entity.getCarteira().getId());
		dto.setIdUsuario(entity.getUsuario().getId());
		
		return dto;
	}

}