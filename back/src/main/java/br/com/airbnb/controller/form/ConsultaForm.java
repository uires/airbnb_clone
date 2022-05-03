package br.com.airbnb.controller.form;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import br.com.airbnb.domain.acomodacao.Espaco;
import br.com.airbnb.domain.acomodacao.TipoLugar;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConsultaForm {
	@NotNull
	private Integer adultos;

	private Integer criancas;

	@Max(value = 5)
	private Integer bebes;

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

}
