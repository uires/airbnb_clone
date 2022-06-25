package br.com.airbnb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.airbnb.controller.form.PagamentoComCartaoForm;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.repository.CartaoRepository;
import br.com.airbnb.service.PagamentoService;
import br.com.airbnb.service.ReservaService;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

	@Autowired
	private ReservaService reservaService;

	@Autowired
	private PagamentoService pagamentoService;

	@Autowired
	private CartaoRepository cartaoRepository;

	@PutMapping("/realiza-pagamento-cartao/{idReserva}")
	public ResponseEntity<Reserva> realizaPagamentoartao(@PathVariable(required = true) Long idReserva,
			@RequestBody PagamentoComCartaoForm form) {

		Reserva reserva = this.reservaService.busca(idReserva);
		reserva = this.pagamentoService.realizaPagamentoViaCartao(reserva,
				form.converte(this.cartaoRepository, reserva));

		return ResponseEntity.ok(reserva);
	}
}
