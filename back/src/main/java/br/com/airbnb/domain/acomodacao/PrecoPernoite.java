package br.com.airbnb.domain.acomodacao;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.airbnb.domain.acomodacao.exception.PrecoPernoiteNaoPodeSerMenorQue74ReaisException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class PrecoPernoite {

	@Getter
	@Column(nullable = false)
	private BigDecimal valor;

	// Flag para verificar se permite desconto de 20% para os três primeiros
	// hóspedes
	private boolean permiteDescontoPrimeirosTresHospedes;

	public PrecoPernoite(BigDecimal valor, boolean permiteDescontoPrimeirosTresHospides) {
		if (valor.compareTo(new BigDecimal("74.00")) < 0) {
			throw new PrecoPernoiteNaoPodeSerMenorQue74ReaisException();
		}

		this.valor = valor;
		this.permiteDescontoPrimeirosTresHospedes = permiteDescontoPrimeirosTresHospides;
	}

	public boolean isPermiteDescontoPrimeirosTresHospides() {
		return permiteDescontoPrimeirosTresHospedes;
	}

}
