package br.com.airbnb.controller.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Hospedes;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservaForm {
	private LocalDateTime dataCriacaoReserva = LocalDateTime.now();

	@NotNull
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm")
	private LocalDateTime fimReserva;

	@NotNull
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm")
	private LocalDateTime inicioReserva;

	@NotNull
	private Integer adultos;

	@NotNull
	private Integer criancas;

	@NotNull
	private Integer bebes;

	@NotNull
	private Integer animais;

	public Reserva converte(UsuarioRepository usuarioRepository, Acomodacao acomodacao) {
		Optional<Usuario> usuario = usuarioRepository.findById(1L);

		if (!usuario.isPresent()) {
			throw new IllegalArgumentException("");
		}

		Hospedes hospedes = new Hospedes(adultos, criancas, bebes, animais);

		return new Reserva(null, inicioReserva, fimReserva, dataCriacaoReserva, BigDecimal.ZERO, BigDecimal.ZERO, false,
				hospedes, usuario.get(), acomodacao);
	}

}
