package com.carteira.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="carteira_itens")
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarteira implements Serializable {
	private static final long serialVersionUID = -685824508777499938L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "id_carteira", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Carteira carteira;
	
	@Column(name="dt_criacao", nullable = false)
	private Date data;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoItemCarteira tipo;
	
	@NotNull
	private String descricao;
	
	@NotNull
	private BigDecimal valor;
	
}
