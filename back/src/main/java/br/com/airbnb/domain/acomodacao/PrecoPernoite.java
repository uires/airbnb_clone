package br.com.airbnb.domain.acomodacao;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

import br.com.airbnb.domain.acomodacao.exception.PrecoPernoiteNaoPodeSerMenorQue74ReaisException;
import lombok.Getter;

@Embeddable
public class PrecoPernoite {

	@Getter
	private BigDecimal valor;

	// Flag para verificar se permite desconto de 20% para os três primeiros
	// hóspedes
	private boolean permiteDescontoPrimeirosTresHospedes;

	public PrecoPernoite(BigDecimal valor, boolean permiteDescontoPrimeirosTresHospides) {
		if (valor.compareTo(new BigDecimal("74.00")) < 74.00) {
			throw new PrecoPernoiteNaoPodeSerMenorQue74ReaisException();
		}

		this.valor = valor;
		this.permiteDescontoPrimeirosTresHospedes = permiteDescontoPrimeirosTresHospides;
	}

	public boolean isPermiteDescontoPrimeirosTresHospides() {
		return permiteDescontoPrimeirosTresHospedes;
	}

}
