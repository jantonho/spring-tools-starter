package com.carteira.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class CarteiraDTO {
	private Long id;
	
	@NotNull(message="O nome não pode ser nulo")
	@Length(min=3, message="O nome deve conter no mínimo 3 caracteres")
	private String nome;
	
	@NotNull(message="Insira um valor para a carteira")
	private BigDecimal valor;
}
