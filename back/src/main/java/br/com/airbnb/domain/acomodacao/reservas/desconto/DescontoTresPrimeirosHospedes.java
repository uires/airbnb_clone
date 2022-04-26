package br.com.airbnb.domain.acomodacao.reservas.desconto;

import java.math.BigDecimal;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;

public class DescontoTresPrimeirosHospedes extends Desconto {

	private final double taxaDesconto = 0.02;

	public DescontoTresPrimeirosHospedes(Desconto proximo) {
		super(proximo);
	}

	@Override
	public BigDecimal calcular(Reserva reserva) {
		if (reserva.getAcomodacao().getPrecoPernoite().isPermiteDescontoPrimeirosTresHospides()
				&& reserva.getAcomodacao().getReservas().size() <= 2) {

			BigDecimal valorTotal = reserva.getValorTotal();
			return valorTotal.subtract(valorTotal.multiply(new BigDecimal(taxaDesconto)));
		}

		return this.proximo.calcular(reserva);
	}

}
