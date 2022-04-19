package br.com.airbnb.domain.acomodacao;

public enum Destaque {
	TRANQUILA("Tranquila"), UNICA("Única"), IDEAL_PARA_FAMILIAS("Ideal para famílias"), CENTRAL("Central");

	private String nome;

	Destaque(String string) {
		this.nome = string;
	}

	public String getNome() {
		return nome;
	}
}
