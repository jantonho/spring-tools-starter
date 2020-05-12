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

import com.carteira.dto.CarteiraDTO;
import com.carteira.dto.UsuarioDTO;
import com.carteira.entity.Carteira;
import com.carteira.entity.Usuario;
import com.carteira.response.Response;
import com.carteira.service.CarteiraService;
import com.carteira.service.UsuarioService;
import com.carteira.util.Bcrypt;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("carteira")
public class CarteiraController {

	@Autowired
	private CarteiraService carteiraService;

	private ObjectMapper mapper = new ObjectMapper();

	@PostMapping
	public ResponseEntity<Response<CarteiraDTO>> create(@Valid @RequestBody CarteiraDTO dto, BindingResult result) {
		Response<CarteiraDTO> response = new Response<CarteiraDTO>();
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		Carteira carteira = carteiraService.save(mapper.convertValue(dto, Carteira.class));
		
		response.setData(mapper.convertValue(carteira, CarteiraDTO.class));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}