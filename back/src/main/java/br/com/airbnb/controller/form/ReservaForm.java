package br.com.airbnb.controller.form;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Hospedes;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservaForm {

	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "YYYY-MM-dd")
	private LocalDateTime fimReserva;

	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "YYYY-MM-dd")
	private LocalDateTime inicioReserva;

	@NotNull
	private Integer adultos;

	@NotNull
	private Integer criancas;

	@NotNull
	private Integer bebes;

	@NotNull
	private Integer animais;

	@Length(max = 1000)
	private String informacoes;

	public Reserva converte(Usuario usuario, Acomodacao acomodacao) {
		Hospedes hospedes = new Hospedes(adultos, criancas, bebes, animais);
		return new Reserva(inicioReserva, fimReserva, LocalDateTime.now(), hospedes, usuario, acomodacao);
	}

}
