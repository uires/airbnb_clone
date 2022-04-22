package br.com.airbnb.domain.acomodacao;

import lombok.Getter;

public enum TipoLugar {
	APARTAMENTO("Apartamento"), CASA("Casa"), UNIDADE_SECUNDARIA("Unidade secundária"),
	ACOMODACAO_UNICA("Acomodação única"), POUSADA("Pousada");
	
	@Getter
	private String nome;

	TipoLugar(String string) {
		this.nome = string;
	}

}
