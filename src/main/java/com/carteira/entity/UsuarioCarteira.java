package com.carteira.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="usuario_carteira")
public class UsuarioCarteira implements Serializable {
	private static final long serialVersionUID = -3400499080987665024L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "id_usuario", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;
	
	@JoinColumn(name = "id_carteira", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Carteira carteira;
}
