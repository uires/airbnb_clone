package br.com.airbnb.domain.acomodacao;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

import br.com.airbnb.domain.acomodacao.exception.PrecoPernoiteNaoPodeSerMenorQue74ReaisException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Precificacao {

	@Getter
	private BigDecimal valor;

	@Getter
	private BigDecimal valorMensal;

	// Flag para verificar se permite desconto de 20% para os três primeiros
	// hóspedes
	private boolean permiteDescontoPrimeirosTresHospedes;

	public Precificacao(BigDecimal valor, BigDecimal valorMensal, boolean permiteDescontoPrimeirosTresHospides) {
		if (valor != null && valorMensal != null) {
			throw new RuntimeException("Não é possível cadastrar uma acomodação com valor cobrança de noite e mensal");
		}

		if (valor != null && valor.compareTo(new BigDecimal("74.00")) < 0) {
			throw new PrecoPernoiteNaoPodeSerMenorQue74ReaisException();
		}

		this.valor = valor;
		this.valorMensal = valorMensal;
		this.permiteDescontoPrimeirosTresHospedes = permiteDescontoPrimeirosTresHospides;
	}

	public boolean isPermiteDescontoPrimeirosTresHospides() {
		return permiteDescontoPrimeirosTresHospedes;
	}

}
