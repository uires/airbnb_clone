package br.com.airbnb.domain.acomodacao;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Hospedes {
	@Getter
	private Integer hospedes;

	@Getter
	private Integer camas;

	@Getter
	private Integer quartos;

	@Getter
	private Integer banheiros;

}
