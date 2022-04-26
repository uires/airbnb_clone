package br.com.airbnb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.airbnb.controller.form.ReservaForm;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.service.ReservaService;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

	@Autowired
	private ReservaService reservaService;

	@PostMapping("/{id}")
	public ResponseEntity<?> cadastrar(@RequestBody ReservaForm form, @PathVariable(required = true) Long id) {

		Reserva reserva = form.converte();
		this.reservaService.cadastra(reserva, id);

		return null;
	}
}
