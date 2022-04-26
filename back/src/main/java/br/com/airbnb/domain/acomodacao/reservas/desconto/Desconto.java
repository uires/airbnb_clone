package br.com.airbnb.domain.acomodacao.reservas.desconto;

import java.math.BigDecimal;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;

public abstract class Desconto {
	protected Desconto proximo;

	public Desconto(Desconto proximo) {
		this.proximo = proximo;
	}

	public abstract BigDecimal calcular(Reserva reserva);
}
