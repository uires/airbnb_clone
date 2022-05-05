package br.com.airbnb.acomodacao.reserva;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
	 * Válida se ao adicionar uma reserva com nova dias a frente o método estoura
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
	 * da acomodação a funcionalidade estoura exception
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
	 * datas de outro a funcionalidade estoura exception
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
	 * estoura exception
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
	 * método estou exception
	 */
	@Test
	public void testaEstouroExceptionReservaComDataFimAntesDeDataInicio() {
		var hospedes = new Hospedes(3, 0, 0, 0);
		var reservaUm = new Reserva(LocalDateTime.now().plusDays(1L), LocalDateTime.of(2021, 12, 1, 1, 30), hospedes,
				new Usuario(), acomodacao);

		assertThrows(IntervaloDeReservaInvalidoException.class, () -> this.acomodacao.adicionaReserva(reservaUm));
	}
	
	
}
