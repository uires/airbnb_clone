package br.com.airbnb.domain.acomodacao.reservas.desconto;

import java.math.BigDecimal;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;

public class FimCadeia extends Desconto {

	public FimCadeia() {
		super(null);
	}

	@Override
	public BigDecimal calcular(Reserva reserva) {
		return BigDecimal.ZERO;
	}

}
