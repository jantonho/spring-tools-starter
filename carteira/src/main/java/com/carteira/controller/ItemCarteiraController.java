package com.carteira.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carteira.dto.ItemCarteiraDTO;
import com.carteira.entity.Carteira;
import com.carteira.entity.ItemCarteira;
import com.carteira.entity.TipoItemCarteira;
import com.carteira.response.Response;
import com.carteira.service.ItemCarteiraService;

@RestController
@RequestMapping("item-carteira")
public class ItemCarteiraController {
	
	private Logger log = LoggerFactory.getLogger(ItemCarteiraController.class);

	@Autowired
	private ItemCarteiraService itemCarteiraService;

	@PostMapping
	public ResponseEntity<Response<ItemCarteiraDTO>> create(@Valid @RequestBody ItemCarteiraDTO dto, BindingResult result) {
		Response<ItemCarteiraDTO> response = new Response<ItemCarteiraDTO>();
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		ItemCarteira ic = itemCarteiraService.salvar(itemDto2Object(dto));
		
		
		response.setData(itemObject2Dto(ic));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	private ItemCarteira itemDto2Object(@Valid ItemCarteiraDTO dto) {
		Carteira carteira = new Carteira();
		carteira.setId(dto.getIdCarteira());
		
		return new ItemCarteira(dto.getId(), carteira, dto.getData(), TipoItemCarteira.getEnum(dto.getTipo()), dto.getDescricao(), dto.getValor());
	}
	
	private ItemCarteiraDTO itemObject2Dto(@Valid ItemCarteira ic) {
		ItemCarteiraDTO dto = new ItemCarteiraDTO();
		dto.setId(ic.getId());
		dto.setData(ic.getData());
		dto.setDescricao(ic.getDescricao());
		dto.setIdCarteira(ic.getCarteira().getId());
		dto.setTipo(ic.getTipo().getNome());
		dto.setValor(ic.getValor());
		return dto;
	}

	@GetMapping("/{carteira}")
	public ResponseEntity<Response<Page<ItemCarteiraDTO>>> buscarPorPeriodo(@PathVariable("carteira") Long carteira
			, @RequestParam("dataIncio") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dataInicio
			, @RequestParam("dataFim") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dataFim
			, @RequestParam(name = "pagina", defaultValue = "0") int pagina) {
		
		Response<Page<ItemCarteiraDTO>> response = new Response<Page<ItemCarteiraDTO>>();
		Page<ItemCarteira> itens = itemCarteiraService.buscarPorPeriodo(carteira, dataInicio, dataFim, pagina);
		Page<ItemCarteiraDTO> dtos = itens.map(i -> itemObject2Dto(i));
		response.setData(dtos);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/tipo/{carteira}")
	public ResponseEntity<Response<List<ItemCarteiraDTO>>> buscarPorCarteiraETipo(@PathVariable("carteira")  Long carteira
			, @RequestParam("tipo") String tipo) {
		
		log.info("Buscando por carteira {} e tipo {}", carteira, tipo);
		
		Response<List<ItemCarteiraDTO>> response = new Response<List<ItemCarteiraDTO>>();
		List<ItemCarteira> list = itemCarteiraService.buscarPorCarteiraETipo(carteira, TipoItemCarteira.getEnum(tipo));
		
		List<ItemCarteiraDTO> dto = new ArrayList<ItemCarteiraDTO>();
		list.forEach(i -> dto.add(itemObject2Dto(i)));
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
		
		ItemCarteira icSalva = itemCarteiraService.salvar(itemDto2Object(dto));
		
		response.setData(itemObject2Dto(icSalva));
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping(value="/{idItemCarteira}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("idItemCarteira") Long idItemCarteira){
		Response<String> response = new Response<String>();
		
		Optional<ItemCarteira> ic = itemCarteiraService.buscarPorId(idItemCarteira);
		
		if(!ic.isPresent()) {
			response.getErrors().add("ItemCarteira não encontrado: id "+ idItemCarteira);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		itemCarteiraService.removerPorId(idItemCarteira);
		response.setData("Item Carteira, id: "+idItemCarteira+", deletado com sucesso");
		
		return ResponseEntity.ok().body(response);


		
	}
}