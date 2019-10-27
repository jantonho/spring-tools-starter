package com.carteira.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTO {
	private Long id;
	
	@Length(min=3, max=50, message="O nome deve conter entre 3 e 50 caracteres")
	private String nome;
	
	@Email(message="Email inválido")
	private String email;
	@NotNull
	@Length(min=6, message="A senha deve conter no minimo 6 caractres")
	private String senha;
}
