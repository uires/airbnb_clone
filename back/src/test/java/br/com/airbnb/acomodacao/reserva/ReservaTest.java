package br.com.airbnb.acomodacao.reserva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Destaque;
import br.com.airbnb.domain.acomodacao.Hospedes;
import br.com.airbnb.domain.acomodacao.Precificacao;
import br.com.airbnb.domain.acomodacao.exception.IntervaloDeReservaInvalidoException;
import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelAdicionaReserva90DiasAFrenteException;
import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelCadastrarUmaReservaNoPassadoException;
import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelReservaSobreporOutraException;
import br.com.airbnb.domain.acomodacao.exception.QuantidadesDeHospedesNaoBateComAcomodacaoException;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;

public class ReservaTest {

	private Acomodacao acomodacao;

	@BeforeEach
	public void criaAcomodacao() {
		var destaques = Arrays.asList(Destaque.CENTRAL);
		var precificacao = new Precificacao(new BigDecimal("500"), null, true);
		var hospedes = new Hospedes(3, 0, 0, 0);
		this.acomodacao = new Acomodacao(null, null, null, null, hospedes, null, precificacao, new BigDecimal("75"),
				new BigDecimal("150"), null, destaques, null, null, null, new ArrayList<Reserva>(), null, null, null,
				false, null, null);
	}

	/**
	 * Válida se ao adicionar uma reserva com nova dias a frente o método estoura
	 * exception
	 */
	@Test
	public void testaEstouroExceptionReservaMaiorQue90Dias() {
		var hospedes = new Hospedes(1, 0, 0, 0);
		var reservaUm = new Reserva(null, LocalDateTime.now().with(LocalTime.NOON).plusDays(91L),
				LocalDateTime.now().plusDays(500L), null, null, BigDecimal.ZERO, false, hospedes, null, acomodacao,
				null, false, null, null, null, null);

		assertThrows(NaoEhPossivelAdicionaReserva90DiasAFrenteException.class,
				() -> this.acomodacao.adicionaReserva(reservaUm));
	}

	/**
	 * Válida se ao adicionar uma reserva com quantidade de hóspedes maior do que a
	 * da acomodação a funcionalidade estoura exception
	 */
	@Test
	public void testaEstouroExceptionQuandoQuantidadeNaoCondizComAcomodacao() {
		var hospedes = new Hospedes(4, 0, 0, 0);
		var reservaUm = new Reserva(null, LocalDateTime.now().with(LocalTime.NOON).plusDays(30L),
				LocalDateTime.now().plusDays(35L), null, null, BigDecimal.ZERO, false, hospedes, null, acomodacao, null,
				false, null, null, null, null);

		assertThrows(QuantidadesDeHospedesNaoBateComAcomodacaoException.class,
				() -> this.acomodacao.adicionaReserva(reservaUm));
	}

	/**
	 * Válida se ao adicionar uma reserva com data de inicio/fim que sobrepoem as
	 * datas de outro a funcionalidade estoura exception
	 */
	@Test
	public void testaEstouExceptionAoSobreporReserva() {
		var hospedes = new Hospedes(3, 0, 0, 0);
		var reservaUm = new Reserva(null, LocalDateTime.now().with(LocalTime.NOON).plusDays(30L),
				LocalDateTime.now().plusDays(35L), null, null, BigDecimal.ZERO, false, hospedes, null, acomodacao, null,
				false, null, null, null, null);

		this.acomodacao.adicionaReserva(reservaUm);
		assertThrows(NaoEhPossivelReservaSobreporOutraException.class,
				() -> this.acomodacao.adicionaReserva(reservaUm));
	}

	/**
	 * Válida se ao adicionar uma reserva com data de inicio no passado a acomodação
	 * estoura exception
	 */
	@Test
	public void testaEstouroExceptionReservaCadastradaNoPassado() {
		var hospedes = new Hospedes(3, 0, 0, 0);
		var reservaUm = new Reserva(null, LocalDateTime.of(2021, 12, 1, 1, 30), LocalDateTime.now().plusDays(1L), null,
				null, BigDecimal.ZERO, false, hospedes, null, acomodacao, null, false, null, null, null, null);

		assertThrows(NaoEhPossivelCadastrarUmaReservaNoPassadoException.class,
				() -> this.acomodacao.adicionaReserva(reservaUm));
	}

	/**
	 * Válida se ao adicionar uma reserva com data de fim antes da data de ínicio o
	 * método estou exception
	 */
	@Test
	public void testaEstouroExceptionReservaComDataFimAntesDeDataInicio() {
		var hospedes = new Hospedes(3, 0, 0, 0);
		var reservaUm = new Reserva(null, LocalDateTime.now().plusDays(1L), LocalDateTime.of(2021, 12, 1, 1, 30), null,
				null, BigDecimal.ZERO, false, hospedes, null, acomodacao, null, false, null, null, null, null);

		assertThrows(IntervaloDeReservaInvalidoException.class, () -> this.acomodacao.adicionaReserva(reservaUm));
	}
}
