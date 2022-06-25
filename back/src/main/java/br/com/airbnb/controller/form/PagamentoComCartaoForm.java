package br.com.airbnb.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import br.com.airbnb.controller.exception.EntityNotFoundException;
import br.com.airbnb.controller.exception.TipoPgtInvalidoException;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.acomodacao.reservas.pagamento.Pagamento;
import br.com.airbnb.domain.acomodacao.reservas.pagamento.PagamentoCartao;
import br.com.airbnb.domain.acomodacao.reservas.pagamento.TipoPagamento;
import br.com.airbnb.domain.usuario.pagamento.Cartao;
import br.com.airbnb.repository.CartaoRepository;
import lombok.Getter;

@Getter
public class PagamentoComCartaoForm {

	@NotNull
	private Long idCartao;
	private TipoPagamento tipoPagamento;

	public Pagamento converte(CartaoRepository cartaoRepository, Reserva reserva) {
		if (tipoPagamento.equals(TipoPagamento.CARTAO_CREDITO) && tipoPagamento.equals(TipoPagamento.CARTAO_DEBITO)) {
			throw new TipoPgtInvalidoException();
		}

		Optional<Cartao> optional = cartaoRepository.findById(idCartao);

		if (!optional.isPresent()) {
			throw new EntityNotFoundException();
		}

		return new PagamentoCartao(null, tipoPagamento, false, null, optional.get(), reserva);
	}

}
