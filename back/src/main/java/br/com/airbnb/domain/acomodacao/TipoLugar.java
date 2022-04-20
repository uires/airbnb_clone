package br.com.airbnb.domain.acomodacao;

public enum TipoLugar {
	APARTAMENTO("Apartamento"), CASA("Casa"), UNIDADE_SECUNDARIA("Unidade secundária"),
	ACOMODACAO_UNICA("Acomodação única"), POUSADA("Pousada");

	private String nome;

	TipoLugar(String string) {
		this.nome = string;
	}

	public String getNome() {
		return nome;
	}

}
