package br.com.airbnb.domain.acomodacao.reservas.desconto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;

/**
 * Define o desconto de 0.1% para cada semana que o hóspede
 * 
 * @author uires
 *
 */
public class DescontoPorSemana extends Desconto {

	private final double TX_DESCONTO_DOZE_SEMANAS = 0.12;

	public DescontoPorSemana(Desconto proximo) {
		super(proximo);
	}

	@Override
	public BigDecimal calcular(Reserva reserva) {
		long semanas = ChronoUnit.WEEKS.between(reserva.getInicioReserva(), reserva.getFimReserva());

		if (semanas > 0 && semanas <= 12) {
			BigDecimal taxaDesconto = new BigDecimal(semanas / 100.00);
			return reserva.getValorTotal().multiply(taxaDesconto).setScale(2, RoundingMode.HALF_UP);
		}

		if (semanas >= 12) {
			BigDecimal taxaDesconto = new BigDecimal(this.TX_DESCONTO_DOZE_SEMANAS);
			return reserva.getValorTotal().multiply(taxaDesconto).setScale(2, RoundingMode.HALF_UP);
		}

		return this.proximo.calcular(reserva);
	}

}
