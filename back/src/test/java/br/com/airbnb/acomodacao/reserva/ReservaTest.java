package br.com.airbnb.acomodacao.reserva;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import br.com.airbnb.domain.acomodacao.exception.ImpossibilidadeCancelarException;
import br.com.airbnb.domain.acomodacao.exception.IntervaloDeReservaInvalidoException;
import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelAdicionaReserva90DiasAFrenteException;
import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelCadastrarUmaReservaNoPassadoException;
import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelReservaSobreporOutraException;
import br.com.airbnb.domain.acomodacao.exception.QuantidadesDeHospedesNaoBateComAcomodacaoException;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.usuario.Usuario;

public class ReservaTest {

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
	 * Válida se ao adicionar uma reserva com nova dias a frente o método lança
	 * exception
	 */
	@Test
	public void testaEstouroExceptionReservaMaiorQue90Dias() {
		Hospedes hospedes = new Hospedes(1, 0, 0, 0);
		var reservaUm = new Reserva(LocalDateTime.now().with(LocalTime.NOON).plusDays(91L),
				LocalDateTime.now().plusDays(500L), hospedes, new Usuario(), acomodacao);

		assertThrows(NaoEhPossivelAdicionaReserva90DiasAFrenteException.class,
				() -> this.acomodacao.adicionaReserva(reservaUm));
	}

	/**
	 * Válida se ao adicionar uma reserva com quantidade de hóspedes maior do que a
	 * da acomodação a funcionalidade lança exception
	 */
	@Test
	public void testaEstouroExceptionQuandoQuantidadeNaoCondizComAcomodacao() {
		var hospedes = new Hospedes(4, 0, 0, 0);
		var reservaUm = new Reserva(LocalDateTime.now().with(LocalTime.NOON).plusDays(30L),
				LocalDateTime.now().plusDays(35L), hospedes, new Usuario(), acomodacao);

		assertThrows(QuantidadesDeHospedesNaoBateComAcomodacaoException.class,
				() -> this.acomodacao.adicionaReserva(reservaUm));
	}

	/**
	 * Válida se ao adicionar uma reserva com data de inicio/fim que sobrepoem as
	 * datas de outro a funcionalidade lança exception
	 */
	@Test
	public void testaEstouExceptionAoSobreporReserva() {
		var hospedes = new Hospedes(3, 0, 0, 0);
		var reservaUm = new Reserva(LocalDateTime.now().with(LocalTime.NOON).plusDays(30L),
				LocalDateTime.now().plusDays(35L), hospedes, new Usuario(), acomodacao);

		this.acomodacao.adicionaReserva(reservaUm);
		assertThrows(NaoEhPossivelReservaSobreporOutraException.class,
				() -> this.acomodacao.adicionaReserva(reservaUm));
	}

	/**
	 * Válida se ao adicionar uma reserva com data de inicio no passado a acomodação
	 * lança exception
	 */
	@Test
	public void testaEstouroExceptionReservaCadastradaNoPassado() {
		var hospedes = new Hospedes(3, 0, 0, 0);
		var reservaUm = new Reserva(LocalDateTime.of(2021, 12, 1, 1, 30), LocalDateTime.now().plusDays(1L), hospedes,
				new Usuario(), acomodacao);

		assertThrows(NaoEhPossivelCadastrarUmaReservaNoPassadoException.class,
				() -> this.acomodacao.adicionaReserva(reservaUm));
	}

	/**
	 * Válida se ao adicionar uma reserva com data de fim antes da data de ínicio o
	 * método lança exception
	 */
	@Test
	public void testaEstouroExceptionReservaComDataFimAntesDeDataInicio() {
		var hospedes = new Hospedes(3, 0, 0, 0);
		var reservaUm = new Reserva(LocalDateTime.now().plusDays(1L), LocalDateTime.of(2021, 12, 1, 1, 30), hospedes,
				new Usuario(), acomodacao);

		assertThrows(IntervaloDeReservaInvalidoException.class, () -> this.acomodacao.adicionaReserva(reservaUm));
	}

	/**
	 * Válida cálculo total sem desconto
	 */
	@Test
	public void testaCalculoTotal() {
		var hospedes = new Hospedes(3, 0, 0, 0);
		this.acomodacao.atualizaAcomodacao(new Precificacao(new BigDecimal("500"), null, false));
		var reservaUm = new Reserva(LocalDateTime.now().plusDays(2L), LocalDateTime.now().plusDays(4L), hospedes,
				new Usuario(), acomodacao);

		this.acomodacao.adicionaReserva(reservaUm);
		assertEquals(new BigDecimal("1225.00"), reservaUm.getValorTotal());
	}

	/**
	 * Válida cálculo total com desconto 20%
	 */
	@Test
	public void testaCalculoTotalComDesconto20() {
		var hospedes = new Hospedes(3, 0, 0, 0);
		var reservaUm = new Reserva(LocalDateTime.now().plusDays(2L), LocalDateTime.now().plusDays(4L), hospedes,
				new Usuario(), acomodacao);

		this.acomodacao.adicionaReserva(reservaUm);
		assertEquals(new BigDecimal("245.00"), reservaUm.getDesconto());
		assertEquals(new BigDecimal("980.00"), reservaUm.getValorTotal());
	}

	/**
	 * Válida cálculo total com desconto por semana
	 */
	@Test
	public void testaCalculoTotalComDescontoSemanal() {
		var hospedes = new Hospedes(3, 0, 0, 0);
		this.acomodacao.atualizaAcomodacao(new Precificacao(new BigDecimal("500"), null, false));
		var reservaUm = new Reserva(LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(50L), hospedes,
				new Usuario(), acomodacao);

		this.acomodacao.adicionaReserva(reservaUm);
		assertEquals(new BigDecimal("1730.75"), reservaUm.getDesconto());
		assertEquals(new BigDecimal("22994.25"), reservaUm.getValorTotal());
	}

	/**
	 * Válida cálculo total com desconto por mês
	 */
	@Test
	public void testaCalculoTotalComDescontoMensal() {
		var hospedes = new Hospedes(3, 0, 0, 0);
		this.acomodacao.atualizaAcomodacao(new Precificacao(new BigDecimal("500"), null, false));
		var reservaUm = new Reserva(LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(95L), hospedes,
				new Usuario(), acomodacao);

		this.acomodacao.adicionaReserva(reservaUm);

		assertEquals(new BigDecimal("3305.75"), reservaUm.getDesconto());
		assertEquals(new BigDecimal("43919.25"), reservaUm.getValorTotal());
	}

	/**
	 * Válida aprovação de reserva
	 */
	@Test
	public void testaAprovacaoReserva() {
		var hospedes = new Hospedes(3, 0, 0, 0);
		this.acomodacao.atualizaAcomodacao(new Precificacao(new BigDecimal("500"), null, false));
		var reservaUm = new Reserva(LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(4L), hospedes,
				new Usuario(), acomodacao);

		this.acomodacao.adicionaReserva(reservaUm);
		reservaUm.aprova();

		assertNotNull(reservaUm.getDataConfirmacaoAnfitriao());
		assertTrue(reservaUm.isReservaConfirmada());
		assertFalse(reservaUm.isReservaCancelada());
	}

	/**
	 * Válida lançamento de exception ao cancelar um reserva cancelada
	 * posteriormente
	 */
	@Test
	public void testaCancelamentoDeReservaDeReserCancelada() {

		var hospedes = new Hospedes(3, 0, 0, 0);
		this.acomodacao.atualizaAcomodacao(new Precificacao(new BigDecimal("500"), null, false));
		var reservaUm = new Reserva(LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(4L), hospedes,
				new Usuario(), acomodacao);

		this.acomodacao.adicionaReserva(reservaUm);

		reservaUm.cancela();
		assertThrows(ImpossibilidadeCancelarException.class, () -> reservaUm.cancela());
	}

}
