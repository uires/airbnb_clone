package br.com.airbnb.domain.acomodacao.reservas.pagamento;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.usuario.pagamento.Cartao;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PagamentoCartao extends Pagamento {

	public PagamentoCartao(Long id, TipoPagamento tipoPagamento, boolean processado, LocalDateTime dataProcessamento,
			Cartao cartao, Reserva reserva) {
		super(id, tipoPagamento, processado, dataProcessamento, reserva);
	}

	@Getter
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "cartao_id", referencedColumnName = "id", nullable = true)
	protected Cartao cartao;

}
