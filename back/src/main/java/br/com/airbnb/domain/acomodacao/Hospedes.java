package br.com.airbnb.domain.acomodacao;

import javax.persistence.Embeddable;

@Embeddable
public class Hospedes {
	private Integer hospedes;
	private Integer camas;
	private Integer quartos;
	private Integer banheiros;

	public Hospedes() { }

	public Hospedes(Integer hospedes, Integer camas, Integer quartos, Integer banheiros) {
		this.hospedes = hospedes;
		this.camas = camas;
		this.quartos = quartos;
		this.banheiros = banheiros;
	}

	public Integer getHospedes() {
		return hospedes;
	}

	public Integer getCamas() {
		return camas;
	}

	public Integer getQuartos() {
		return quartos;
	}

	public Integer getBanheiros() {
		return banheiros;
	}

}
