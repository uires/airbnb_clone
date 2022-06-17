package br.com.airbnb.domain.usuario.pagamento;

import lombok.Getter;

public enum Bandeira {
	MASTERCARD("Mastercard"), VISA("Visa"), AMERICAN_EXPRESS("American Express"), HIPERCARD("Hipercard"), ELO("Elo");

	@Getter
	private String nome;

	Bandeira(String string) {
		this.nome = string;
	}

}
