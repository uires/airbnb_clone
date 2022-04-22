package br.com.airbnb.domain.acomodacao;

import lombok.Getter;

public enum Espaco {
	PISCINA("Piscina"), JACUZZI("Jacuzzi"), PATIO("Pátio"), CHURRASQUEIRA("Churrasqueira"),
	MESA_DE_BILHAR("Mesa de bilha"), AREA_DE_JANTAR_EXTERNA("Área de jantar externa");

	@Getter
	private String nome;

	Espaco(String string) {
		this.nome = string;
	}
}
