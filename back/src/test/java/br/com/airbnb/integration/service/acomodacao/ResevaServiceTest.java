package br.com.airbnb.integration.service.acomodacao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Destaque;
import br.com.airbnb.domain.acomodacao.Hospedes;
import br.com.airbnb.domain.acomodacao.Precificacao;
import br.com.airbnb.domain.acomodacao.QuantidadesComodos;
import br.com.airbnb.domain.acomodacao.TipoLugar;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.repository.AcomodacaoRepository;
import br.com.airbnb.repository.UsuarioRepository;
import br.com.airbnb.service.ReservaService;
import br.com.airbnb.service.exception.NaoPossuiPermissaoAlterarException;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class ResevaServiceTest {

	@Autowired
	private AcomodacaoRepository acomodacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ReservaService reservaService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	private Reserva reservaAprovacao;
	private Reserva reservaCancelamentoHospede;
	private Reserva reservaCancelamentoResponsavelAcomodacao;

	private Usuario responsavelAcomodacao;
	private Usuario responsavelReserva;
	private Usuario usuarioAleatorio;

	@BeforeAll
	public void setAcomodacao() {
		Hospedes hospedes = new Hospedes(2, 1, 0, 0);
		Precificacao precificacao = new Precificacao(new BigDecimal("500.00"), null, false);
		QuantidadesComodos quantidadesComodos = new QuantidadesComodos(3, 2);

		String senha = encoder.encode("123456");
		Usuario usuario = new Usuario(null, "First", "Second", null, "ddd@gmail.com", LocalDate.now(), false, false,
				senha, null, null);

		Usuario usuarioDois = new Usuario(null, "Second", "First", null, "ccc@gmail.com", LocalDate.now(), false, false,
				senha, null, null);

		Usuario usuarioTres = new Usuario(null, "Second", "First", null, "aaa@gmail.com", LocalDate.now(), false, false,
				senha, null, null);

		usuario = this.usuarioRepository.save(usuario);
		usuarioDois = this.usuarioRepository.save(usuarioDois);
		usuarioTres = this.usuarioRepository.save(usuarioTres);

		Acomodacao acomodacao = new Acomodacao(TipoLugar.APARTAMENTO, null, hospedes, null, precificacao,
				new BigDecimal("75.00"), new BigDecimal("150.00"), "Espaço Juremana é um espaço maravilhoso",
				Arrays.asList(Destaque.IDEAL_PARA_FAMILIAS), "Espaço Juremana", usuario, LocalTime.NOON, LocalTime.NOON,
				false, quantidadesComodos, 1);

		var reservaAprovacao = new Reserva(LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(4L),
				LocalDateTime.now(), new Hospedes(2, 0, 0, 0), usuarioDois, acomodacao);

		var reservaCancelamentoHospede = new Reserva(LocalDateTime.now().plusDays(10L),
				LocalDateTime.now().plusDays(15L), LocalDateTime.now(), new Hospedes(2, 0, 0, 0), usuarioDois,
				acomodacao);

		var reservaCancelamentoResponsavelAcomodacao = new Reserva(LocalDateTime.now().plusDays(20L),
				LocalDateTime.now().plusDays(20L), LocalDateTime.now(), new Hospedes(2, 0, 0, 0), usuarioDois,
				acomodacao);

		acomodacao.adicionaReserva(reservaAprovacao);
		acomodacao.adicionaReserva(reservaCancelamentoHospede);
		acomodacao.adicionaReserva(reservaCancelamentoResponsavelAcomodacao);

		this.acomodacaoRepository.save(acomodacao);

		this.reservaAprovacao = reservaAprovacao;
		this.reservaCancelamentoHospede = reservaCancelamentoHospede;
		this.reservaCancelamentoResponsavelAcomodacao = reservaCancelamentoResponsavelAcomodacao;

		this.responsavelAcomodacao = usuario;
		this.responsavelReserva = usuarioDois;
		this.usuarioAleatorio = usuarioTres;
	}

	private void mockSecurityContextHolder(Usuario usuario) {
		SecurityContext securityContext = new SecurityContextImpl();
		securityContext
				.setAuthentication(new TestingAuthenticationToken(usuario, null, new ArrayList<GrantedAuthority>()));
		SecurityContextHolder.setContext(securityContext);
	}

	@Test
	void testaPermissaoOwernerAoAprovarReserva() {
		this.mockSecurityContextHolder(responsavelReserva);
		assertThrows(NaoPossuiPermissaoAlterarException.class,
				() -> this.reservaService.aprovaReserva(this.reservaAprovacao));
	}

	@Test
	void testaAprovacaoReservaComUsuarioResponsavel() {
		this.mockSecurityContextHolder(responsavelAcomodacao);
		Reserva reservaAuxiliar = this.reservaService.aprovaReserva(this.reservaAprovacao);

		assertTrue(reservaAuxiliar.isReservaConfirmada());
		assertFalse(reservaAuxiliar.isReservaCancelada());

		assertNull(reservaAuxiliar.getDataCancelamento());
		assertNotNull(reservaAuxiliar.getDataConfirmacaoAnfitriao());
		assertDoesNotThrow(() -> NaoPossuiPermissaoAlterarException.class);
	}

	@Test
	void testaCancelamentoReservaComResponsavelAcomodacao() {
		this.mockSecurityContextHolder(responsavelAcomodacao);
		Reserva reservaAuxiliar = this.reservaService.cancelaReserva(this.reservaCancelamentoResponsavelAcomodacao);

		assertFalse(reservaAuxiliar.isReservaConfirmada());
		assertNull(reservaAuxiliar.getDataConfirmacaoAnfitriao());
		assertNotNull(reservaAuxiliar.getDataCancelamento());
		assertTrue(reservaAuxiliar.isReservaCancelada());

		assertDoesNotThrow(() -> NaoPossuiPermissaoAlterarException.class);
	}

	@Test
	void testaCancelamentoReservaComResponsavelReserva() {
		this.mockSecurityContextHolder(responsavelReserva);
		Reserva reservaAuxiliar = this.reservaService.cancelaReserva(this.reservaCancelamentoHospede);

		assertFalse(reservaAuxiliar.isReservaConfirmada());
		assertNull(reservaAuxiliar.getDataConfirmacaoAnfitriao());
		assertNotNull(reservaAuxiliar.getDataCancelamento());
		assertTrue(reservaAuxiliar.isReservaCancelada());

		assertDoesNotThrow(() -> NaoPossuiPermissaoAlterarException.class);
	}

	@Test
	void testaCancelamentoReservaComUsuarioAleatorio() {
		this.mockSecurityContextHolder(usuarioAleatorio);
		assertThrows(NaoPossuiPermissaoAlterarException.class,
				() -> this.reservaService.cancelaReserva(this.reservaCancelamentoResponsavelAcomodacao));
	}

}
