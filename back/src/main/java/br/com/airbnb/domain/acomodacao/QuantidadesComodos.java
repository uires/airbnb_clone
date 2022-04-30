package br.com.airbnb.domain.acomodacao;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class QuantidadesComodos {

	@Column(nullable = false)
	@Getter
	private Integer quartos;

	@Column(nullable = false)
	@Getter
	private Integer banheiros;
}
