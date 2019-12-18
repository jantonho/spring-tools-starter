package com.carteira.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carteira.dto.ItemCarteiraDTO;
import com.carteira.entity.ItemCarteira;
import com.carteira.entity.TipoItemCarteira;
import com.carteira.response.Response;
import com.carteira.service.ItemCarteiraService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("item-carteira")
public class ItemCarteiraController {

	@Autowired
	private ItemCarteiraService itemCarteiraService;

	private ObjectMapper mapper = new ObjectMapper();

	@PostMapping
	public ResponseEntity<Response<ItemCarteiraDTO>> create(@Valid @RequestBody ItemCarteiraDTO dto, BindingResult result) {
		Response<ItemCarteiraDTO> response = new Response<ItemCarteiraDTO>();
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		ItemCarteira ic = itemCarteiraService.salvar(mapper.convertValue(dto, ItemCarteira.class));
		
		
		response.setData(mapper.convertValue(ic, ItemCarteiraDTO.class));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/{carteira}")
	public ResponseEntity<Response<Page<ItemCarteiraDTO>>> buscarPorPeriodo(@PathVariable("carteira") Long carteira
			, @RequestParam("dataIncio") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dataInicio
			, @RequestParam("dataFim") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dataFim
			, @RequestParam(name = "pagina", defaultValue = "0") int pagina) {
		
		Response<Page<ItemCarteiraDTO>> response = new Response<Page<ItemCarteiraDTO>>();
		Page<ItemCarteira> itens = itemCarteiraService.buscarPorPeriodo(carteira, dataInicio, dataFim, pagina);
		Page<ItemCarteiraDTO> dtos = itens.map(i -> mapper.convertValue(i, ItemCarteiraDTO.class));
		response.setData(dtos);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/tipo/{carteira}")
	public ResponseEntity<Response<List<ItemCarteiraDTO>>> buscarPorCarteiraETipo(@PathVariable("carteira")  Long carteira
			, @RequestParam("tipo") String tipo) {
		Response<List<ItemCarteiraDTO>> response = new Response<List<ItemCarteiraDTO>>();
		List<ItemCarteira> list = itemCarteiraService.buscarPorCarteiraETipo(carteira, TipoItemCarteira.getEnum(tipo));
		
		List<ItemCarteiraDTO> dto = new ArrayList<ItemCarteiraDTO>();
		list.forEach(i -> dto.add(mapper.convertValue(i, ItemCarteiraDTO.class)));
		response.setData(dto);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "/total/{carteira}")
	public ResponseEntity<Response<BigDecimal>> somarPorCarteira(@PathVariable("carteira") Long carteira) {
		Response<BigDecimal> response = new Response<BigDecimal>();
		
		BigDecimal valor = itemCarteiraService.SomarPorCarteira(carteira);
		response.setData(valor == null ? BigDecimal.ZERO : valor);
		
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<ItemCarteiraDTO>> atualizar(@Valid @RequestBody ItemCarteiraDTO dto, BindingResult result) {
		Response<ItemCarteiraDTO> response = new Response<ItemCarteiraDTO>();
		
		Optional<ItemCarteira> ic = itemCarteiraService.buscarPorId(dto.getId());
		
		if(!ic.isPresent()) {
			result.addError(new ObjectError("ItemCarteira", "ItemCarteira não encontrado"));
		} else if(ic.get().getCarteira().getId().compareTo(dto.getIdCarteira()) != 0) {
			result.addError(new ObjectError("ItemCarteiraAlterado", "Você não pode alterar a carteira"));
		}
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		ItemCarteira icSalva = itemCarteiraService.salvar(mapper.convertValue(dto, ItemCarteira.class));
		
		response.setData(mapper.convertValue(icSalva, ItemCarteiraDTO.class));
		return ResponseEntity.ok().body(response);
	}
}