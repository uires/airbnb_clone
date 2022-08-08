package br.com.airbnb.unit.acomodacao.reserva.pagamento;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Destaque;
import br.com.airbnb.domain.acomodacao.Hospedes;
import br.com.airbnb.domain.acomodacao.Precificacao;
import br.com.airbnb.domain.acomodacao.exception.PagamentoRealizadoException;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.acomodacao.reservas.pagamento.PagamentoBoleto;
import br.com.airbnb.domain.acomodacao.reservas.pagamento.PagamentoCartao;
import br.com.airbnb.domain.acomodacao.reservas.pagamento.TipoPagamento;
import br.com.airbnb.domain.usuario.Usuario;

public class PagamentoTest {

	private Acomodacao acomodacao;
	private Reserva reserva;

	@BeforeEach
	public void criaReserva() {
		List<Destaque> destaques = Arrays.asList(Destaque.CENTRAL);
		Precificacao precificacao = new Precificacao(new BigDecimal("500"), null, true);
		Hospedes hospedes = new Hospedes(3, 0, 0, 0);
		this.acomodacao = new Acomodacao(null, null, hospedes, null, precificacao, new BigDecimal("75"),
				new BigDecimal("150"), null, destaques, null, null, LocalTime.NOON, LocalTime.NOON, false, null, null);

		this.acomodacao.atualizaAcomodacao(new Precificacao(new BigDecimal("500"), null, false));
		this.reserva = new Reserva(LocalDateTime.now().plusDays(2L), LocalDateTime.now().plusDays(4L),
				LocalDateTime.now(), hospedes, new Usuario(), acomodacao);

		this.acomodacao.adicionaReserva(reserva);
	}

	@Test
	public void testLancamentoExceptionAoAdicionarPagamentoDuasVezes() {
		this.reserva
				.adicionaPagamento(new PagamentoCartao(null, TipoPagamento.CARTAO_CREDITO, false, null, null, reserva));
		
		assertNotNull(this.reserva.getPagamento());
		assertThrows(PagamentoRealizadoException.class, () -> this.reserva.adicionaPagamento(
				new PagamentoCartao(null, TipoPagamento.CARTAO_CREDITO, false, null, null, reserva)));
	}

	@Test
	public void testPagamentoProcessadoAoAdicionarCartao() {
		this.reserva
				.adicionaPagamento(new PagamentoCartao(null, TipoPagamento.CARTAO_CREDITO, false, null, null, reserva));

		assertNotNull(this.reserva.getPagamento());
		assertTrue(this.reserva.getPagamento().isProcessado());
	}

	@Test
	public void testPagamentoNaoProcessadoAoAdicionarPagamentoBoleto() {
		this.reserva.adicionaPagamento(
				new PagamentoBoleto(null, TipoPagamento.CARTAO_CREDITO, false, null, null, reserva, ""));

		assertNotNull(this.reserva.getPagamento());
		assertFalse(this.reserva.getPagamento().isProcessado());
	}

}
