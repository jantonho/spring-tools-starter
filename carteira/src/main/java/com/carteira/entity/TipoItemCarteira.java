package com.carteira.entity;

public enum TipoItemCarteira {

	EN("ENTRADA"),
	SD("SAIDA");
	
	private final String nome;
	
	TipoItemCarteira(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public static TipoItemCarteira getEnum(String nome) {
		for(TipoItemCarteira tic : values()) {
			if(nome.equals(tic.getNome())) {
				return tic;
			}
		}
		
		return null;
	}
}
