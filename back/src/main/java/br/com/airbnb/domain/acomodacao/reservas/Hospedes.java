package br.com.airbnb.domain.acomodacao.reservas;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@AllArgsConstructor
public class Hospedes {

	@Getter
	@Column(nullable = false)
	private Integer adultos;
	@Getter
	@Column(nullable = false)
	private Integer criancas;

	@Getter
	@Column(nullable = false)
	private Integer bebes;

	@Getter
	@Column(nullable = false)
	private Integer animais;
}
