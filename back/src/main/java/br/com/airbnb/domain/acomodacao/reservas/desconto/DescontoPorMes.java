package br.com.airbnb.domain.acomodacao.reservas.desconto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;

/**
 * Define o desconto de 0.3% para cada mês de reserva
 * 
 * @author uires
 *
 */
public class DescontoPorMes extends Desconto {

	private final double TX_DESCONTO_MES = 0.03;

	public DescontoPorMes(Desconto proximo) {
		super(proximo);
	}

	@Override
	public BigDecimal calcular(Reserva reserva) {
		long meses = ChronoUnit.MONTHS.between(reserva.getInicioReserva(), reserva.getFimReserva());

		if (meses > 0) {
			BigDecimal taxaDesconto = new BigDecimal(this.TX_DESCONTO_MES);
			return reserva.getValorTotal().multiply(taxaDesconto).setScale(2, RoundingMode.HALF_UP);
		}

		return this.proximo.calcular(reserva);
	}

}
