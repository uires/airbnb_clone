package br.com.airbnb.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.acomodacao.reservas.avaliacao.Avaliacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoForm {

	@NotNull
	private BigDecimal notaLimpeza;

	@NotNull
	private BigDecimal notaComunicacao;

	@NotNull
	private BigDecimal notaCheckIn;

	@NotNull
	private BigDecimal notaExatidaoDoAnuncio;

	@NotNull
	private BigDecimal notaLocalizacao;

	@NotNull
	private BigDecimal notaCustoBenefico;

	private String comentario;

	public Avaliacao converte(Reserva reserva) {
		return new Avaliacao(null, notaLimpeza, notaComunicacao, notaCheckIn, notaExatidaoDoAnuncio, notaLocalizacao,
				notaCustoBenefico, comentario, reserva, reserva.getAcomodacao());
	}
}
