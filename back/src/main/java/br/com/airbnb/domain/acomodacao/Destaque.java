package br.com.airbnb.domain.acomodacao;

import lombok.Getter;

public enum Destaque {
	TRANQUILA("Tranquila"), UNICA("Única"), IDEAL_PARA_FAMILIAS("Ideal para famílias"), CENTRAL("Central");
	
	@Getter
	private String nome;

	Destaque(String string) {
		this.nome = string;
	}

}
