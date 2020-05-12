package com.carteira.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UsuarioCarteiraDTO {
	private Long id;
	
	@NotNull(message="Informe o id do Usuário")
	private Long idUsuario;
	
	@NotNull(message="Informe o id da Carteira")
	private Long idCarteira;
}
