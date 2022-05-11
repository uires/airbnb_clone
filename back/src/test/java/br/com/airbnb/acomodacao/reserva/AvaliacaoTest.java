package br.com.airbnb.acomodacao.reserva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Destaque;
import br.com.airbnb.domain.acomodacao.Hospedes;
import br.com.airbnb.domain.acomodacao.Precificacao;
import br.com.airbnb.domain.acomodacao.exception.NaoPodeAvaliarException;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.acomodacao.reservas.avaliacao.Avaliacao;
import br.com.airbnb.domain.usuario.Usuario;

public class AvaliacaoTest {

	private Acomodacao acomodacao;

	@BeforeEach
	public void criaAcomodacao() {
		List<Destaque> destaques = Arrays.asList(Destaque.CENTRAL);
		Precificacao precificacao = new Precificacao(new BigDecimal("500"), null, true);
		Hospedes hospedes = new Hospedes(3, 0, 0, 0);
		this.acomodacao = new Acomodacao(null, null, hospedes, null, precificacao, new BigDecimal("75"),
				new BigDecimal("150"), null, destaques, null, null, null, null, false, null, null);
	}

	/**
	 * Válida cadastro de avalição no domínio da reserva
	 */
	@Test
	public void testaCadastroAvaliacao() {
		var hospedes = new Hospedes(3, 0, 0, 0);
		var reservaUm = new Reserva(LocalDateTime.now().plusDays(2L), LocalDateTime.now().plusDays(4L),
				LocalDateTime.now(), hospedes, new Usuario(), acomodacao);

		var avaliacao = new Avaliacao(null, new BigDecimal("5.0"), new BigDecimal("4.9"), new BigDecimal("3.1"),
				new BigDecimal("3.1"), new BigDecimal("2.9"), new BigDecimal("1.1"), "Um local maravilhoso", reservaUm,
				acomodacao);

		reservaUm.adicionaAvaliacao(avaliacao);
		assertEquals(new BigDecimal("5.0"), reservaUm.getAvaliacao().getNotaLimpeza());
		assertEquals(new BigDecimal("4.9"), reservaUm.getAvaliacao().getNotaComunicacao());
		assertEquals(new BigDecimal("3.1"), reservaUm.getAvaliacao().getNotaCheckIn());
		assertEquals(new BigDecimal("3.1"), reservaUm.getAvaliacao().getNotaExatidaoDoAnuncio());
		assertEquals(new BigDecimal("2.9"), reservaUm.getAvaliacao().getNotaLocalizacao());
		assertEquals(new BigDecimal("1.1"), reservaUm.getAvaliacao().getNotaCustoBenefico());
		assertEquals("Um local maravilhoso", reservaUm.getAvaliacao().getComentario());
	}

	/**
	 * Válida lançamento de excetion ao tentar cadastrar mais de uma avalição ao
	 * domínio de reserva
	 */
	@Test
	public void testaLancamentoExceptionAoTentarCadastrarMaisDeUmaAvaliacao() {
		var hospedes = new Hospedes(3, 0, 0, 0);
		var reservaUm = new Reserva(LocalDateTime.now().plusDays(2L), LocalDateTime.now().plusDays(4L),
				LocalDateTime.now(), hospedes, new Usuario(), acomodacao);

		var avaliacao = new Avaliacao(null, new BigDecimal("5.0"), new BigDecimal("4.9"), new BigDecimal("3.1"),
				new BigDecimal("3.1"), new BigDecimal("2.9"), new BigDecimal("1.1"), "Um local maravilhoso", reservaUm,
				acomodacao);

		reservaUm.adicionaAvaliacao(avaliacao);
		assertThrows(NaoPodeAvaliarException.class, () -> reservaUm.adicionaAvaliacao(avaliacao));
	}
}
