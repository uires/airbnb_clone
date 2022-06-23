package br.com.airbnb.controller.form;

import java.time.LocalDateTime;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.acomodacao.reservas.pagamento.TipoPagamento;
import br.com.airbnb.domain.usuario.pagamento.Cartao;

public class PagamentoForm {

	private TipoPagamento tipoPagamento;
	private LocalDateTime dataProcessamento;
	private Cartao cartao;
	private Reserva reserva;

}
