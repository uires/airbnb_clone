package br.com.airbnb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.airbnb.controller.form.AvaliacaoForm;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.acomodacao.reservas.avaliacao.Avaliacao;
import br.com.airbnb.service.AvaliacaoService;
import br.com.airbnb.service.ReservaService;

@RequestMapping("/avaliacao")
@RestController
public class AvaliacaoController {

	@Autowired
	private ReservaService serviceReserva;

	@Autowired
	private AvaliacaoService avaliacaoService;

	@PostMapping("/avalia/{id}")
	public ResponseEntity<Avaliacao> avalia(@RequestBody AvaliacaoForm avalicao,
			@PathVariable(required = true) Long id) {
		Reserva reserva = this.serviceReserva.busca(id);
		Avaliacao avaliacao = avalicao.converte(reserva);
		avaliacao = this.avaliacaoService.cadastra(reserva, avaliacao);
		return ResponseEntity.ok(avaliacao);
	}

}
