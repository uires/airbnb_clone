package br.com.airbnb.domain.acomodacao.reservas.desconto;

import java.math.BigDecimal;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;

/**
 * Define o desconto de 20% para os três primeiros hóspedes
 * 
 * @author uires
 *
 */
public class DescontoTresPrimeirosHospedes extends Desconto {

	private final double TAXA_DESCONTO = 0.20;

	public DescontoTresPrimeirosHospedes(Desconto proximo) {
		super(proximo);
	}

	@Override
	public BigDecimal calcular(Reserva reserva) {
		if (reserva.getAcomodacao().getPrecificacao().isPermiteDescontoPrimeirosTresHospides()
				&& reserva.getAcomodacao().getReservas().size() <= 2) {

			BigDecimal valorTotal = reserva.getValorTotal();
			return valorTotal.subtract(valorTotal.multiply(new BigDecimal(this.TAXA_DESCONTO)));
		}

		return this.proximo.calcular(reserva);
	}

}
