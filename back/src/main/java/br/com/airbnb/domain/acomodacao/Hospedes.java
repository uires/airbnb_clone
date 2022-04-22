package br.com.airbnb.domain.acomodacao;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Hospedes {
	@Getter
	@Column(nullable = false)
	private Integer hospedes;

	@Getter
	@Column(nullable = false)
	private Integer camas;

	@Getter
	@Column(nullable = false)
	private Integer quartos;

	@Getter
	@Column(nullable = false)
	private Integer banheiros;

}
