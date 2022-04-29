package br.com.airbnb.domain.acomodacao.reservas.desconto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
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

	public static void main(String[] args) {
		DescontoPorMes descontoPorMes = new DescontoPorMes(null);
		Reserva reserva = new Reserva(null, LocalDateTime.of(2022, 03, 1, 13, 30, 59),
				LocalDateTime.of(2027, 05, 1, 13, 30, 59), LocalDateTime.now(), null, new BigDecimal("7820"), false, 3,
				null, null);
		reserva.calculaTotal();
		System.out.println(descontoPorMes.calcular(reserva));
		reserva.aplicaDesconto(descontoPorMes.calcular(reserva));
		System.out.println(reserva.getValorTotal());
	}

}
