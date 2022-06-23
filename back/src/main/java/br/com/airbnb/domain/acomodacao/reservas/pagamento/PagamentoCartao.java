package br.com.airbnb.domain.acomodacao.reservas.pagamento;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.usuario.pagamento.Cartao;

@Entity
public class PagamentoCartao extends Pagamento {

	public PagamentoCartao(Long id, TipoPagamento tipoPagamento, boolean processado, LocalDateTime dataProcessamento,
			Cartao cartao, Reserva reserva) {
		super(id, tipoPagamento, processado, dataProcessamento, cartao, reserva);
	}

}
