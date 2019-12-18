package com.carteira.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ItemCarteiraDTO {
	private Long id;
	
	
	@NotNull(message = "Insira o id da carteira")
	private Long idCarteira;

	@NotNull(message = "Informe a data")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private Date data;
	
	@NotNull(message = "Informe o tipo")
	@Pattern(regexp = "^(ENTRADA|SAIDA)$", message = "Tipo só aceita ENTADA ou SAIDA")
	private String tipo;
	
	@NotNull(message = "Informe a descrição")
	@Length(min = 5, message = "A descrição deve conter no minimo 5 caracteres")
	private String descricao;
	
	@NotNull(message = "Informe o valor")
	private BigDecimal valor;
	
}
