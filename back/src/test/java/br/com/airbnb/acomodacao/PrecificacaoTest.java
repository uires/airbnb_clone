package br.com.airbnb.acomodacao;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.com.airbnb.domain.acomodacao.Precificacao;
import br.com.airbnb.domain.acomodacao.exception.PrecoPernoiteNaoPodeSerMenorQue74ReaisException;

public class PrecificacaoTest {

	/**
	 * Válida a estouro de exception ao criar precificação de acomodação com preço
	 * menor que R$ 74,00
	 */
	@Test
	public void testaCriacaoAcomodacaoComPrecificacaoMenorIgual74() {
		assertThrows(PrecoPernoiteNaoPodeSerMenorQue74ReaisException.class,
				() -> new Precificacao(new BigDecimal("73.99"), null, false));
	}

	/**
	 * Válida a estouro de exception ao criar precificação de acomodação com preço
	 * de mês e noite definidos
	 */
	@Test
	public void testaCriacaoAcomodacaoComPrecificacaoMensalENoturnaDefinidas() {
		assertThrows(RuntimeException.class,
				() -> new Precificacao(new BigDecimal("500"), new BigDecimal("500"), false));
	}
}
