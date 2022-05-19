package br.com.airbnb.controller.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.airbnb.domain.acomodacao.Espaco;
import br.com.airbnb.domain.acomodacao.TipoLugar;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConsultaForm {

	@Max(value = 5)
	private Integer adultos;

	private Integer criancas;

	@Max(value = 5)
	private Integer bebes;

	private boolean permiteAnimais = true;

	@Max(value = 5)
	private Integer animais;

	@NotNull
	private BigDecimal faxaPrecoInicial;

	@NotNull
	private BigDecimal faxaPrecoFinal;

	private TipoLugar lugar;

	private Integer quantidadeCamas;
	private Integer quantidadeQuartos;

	private List<Espaco> comodidades;

	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "YYYY-MM-dd")
	private LocalDateTime inicioReserva;

	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "YYYY-MM-dd")
	private LocalDateTime fimReserva;

}
