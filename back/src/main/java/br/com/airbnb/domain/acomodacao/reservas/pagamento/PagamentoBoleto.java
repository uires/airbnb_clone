package br.com.airbnb.domain.acomodacao.reservas.pagamento;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.usuario.pagamento.Cartao;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PagamentoBoleto extends Pagamento {

	@Getter
	private String numeroBoleto;

	public PagamentoBoleto(Long id, TipoPagamento tipoPagamento, boolean processado, LocalDateTime dataProcessamento,
			Cartao cartao, Reserva reserva, String numeroBoleto) {
		super(id, tipoPagamento, processado, dataProcessamento, cartao, reserva);
		this.numeroBoleto = numeroBoleto;
	}

}
