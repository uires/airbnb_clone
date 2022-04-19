package br.com.airbnb.domain.acomodacao;

public enum Espaco {
	PISCINA("Piscina"), JACUZZI("Jacuzzi"), PATIO("Pátio"), CHURRASQUEIRA("Churrasqueira"),
	MESA_DE_BILHAR("Mesa de bilha"), AREA_DE_JANTAR_EXTERNA("Área de jantar externa");

	private String nome;

	Espaco(String string) {
		this.nome = string;
	}

	public String getNome() {
		return nome;
	}

}
