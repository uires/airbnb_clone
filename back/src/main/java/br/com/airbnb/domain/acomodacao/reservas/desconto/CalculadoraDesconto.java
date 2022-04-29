package br.com.airbnb.domain.acomodacao.reservas.desconto;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;

/**
 * Classe para gerencia chamada em cadeia dos descontos
 * 
 * @author uires
 *
 */
public class CalculadoraDesconto {

	public Reserva calculaDesconto(Reserva reserva) {
		Desconto desconto = new DescontoTresPrimeirosHospedes(
				new DescontoPorSemana(new DescontoPorMes(new FimCadeia())));
		reserva.aplicaDesconto(desconto.calcular(reserva));

		return reserva;
	}
}
