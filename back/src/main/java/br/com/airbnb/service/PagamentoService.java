package br.com.airbnb.service;

import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.acomodacao.reservas.pagamento.Pagamento;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.service.exception.CartaoInvalidoException;

@Service
public class PagamentoService {

	@Transactional
	public Reserva realizaPagamentoViaCartao(Reserva reserva, Pagamento pagamento) {
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (!pagamento.getCartao().getUsuario().getId().equals(usuario.getId())) {
			throw new CartaoInvalidoException();
		}

		reserva.adicionaPagamento(pagamento);

		return reserva;
	}

}
