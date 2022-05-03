package br.com.airbnb.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.airbnb.controller.exception.EntityNotFoundException;
import br.com.airbnb.controller.form.ReservaForm;
import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.repository.UsuarioRepository;
import br.com.airbnb.service.AcomodacaoService;
import br.com.airbnb.service.ReservaService;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

	@Autowired
	private ReservaService reservaService;

	@Autowired
	private AcomodacaoService acomodacaoService;

	@Autowired
	private UsuarioRepository repository;

	@PostMapping("/{id}")
	public ResponseEntity<Reserva> cadastrar(@RequestBody @Valid ReservaForm form,
			@PathVariable(required = true) Long id) {
		Optional<Acomodacao> acomodacaoOptional = acomodacaoService.busca(id);
		if (!acomodacaoOptional.isPresent()) {
			throw new EntityNotFoundException();
		}

		Acomodacao acomodacao = acomodacaoOptional.get();
		Reserva reserva = form.converte(repository, acomodacao);
		reserva = this.reservaService.cadastra(reserva, acomodacao);

		return ResponseEntity.ok(reserva);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Reserva> cancela(@PathVariable(required = true) Long id) {
		Reserva reserva = this.reservaService.busca(id);
		reserva = this.reservaService.cancelaReserva(reserva);
		return ResponseEntity.ok(reserva);
	}

	@PutMapping("aprova-reserva/{id}")
	public ResponseEntity<?> aprovaReserva(@PathVariable Long id) {
		Reserva reserva = this.reservaService.busca(id);
		reserva = this.reservaService.aprovaReserva(reserva);
		return ResponseEntity.noContent().build();
	}

}
