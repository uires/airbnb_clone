package br.com.airbnb.domain.acomodacao;

import java.math.BigDecimal;

import br.com.airbnb.domain.acomodacao.exception.PrecoPernoiteNaoPodeSerMenorQue74ReaisException;

public class PrecoPernoite {

	private BigDecimal valor;
	// Flag para verificar se permite desconto de 20% para os três primeiros
	// hóspedes
	private boolean permiteDescontoPrimeirosTresHospides;

	public PrecoPernoite(BigDecimal valor, boolean permiteDescontoPrimeirosTresHospides) {
		if (valor.compareTo(new BigDecimal("74.00")) < 74.00) {
			throw new PrecoPernoiteNaoPodeSerMenorQue74ReaisException();
		}

		this.valor = valor;
		this.permiteDescontoPrimeirosTresHospides = permiteDescontoPrimeirosTresHospides;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public boolean isPermiteDescontoPrimeirosTresHospides() {
		return permiteDescontoPrimeirosTresHospides;
	}

}
