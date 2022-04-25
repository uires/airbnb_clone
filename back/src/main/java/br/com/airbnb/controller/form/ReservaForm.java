package br.com.airbnb.controller.form;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservaForm {
	private LocalDateTime dataCriacaoReserva = LocalDateTime.now();
	
	@NotNull
	private LocalDateTime fimReserva;
	@NotNull
	private LocalDateTime inicioReserva;
	@NotNull
	private Integer quantidadeHospedes;
}
