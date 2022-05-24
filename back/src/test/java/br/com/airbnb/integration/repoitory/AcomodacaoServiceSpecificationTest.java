package br.com.airbnb.integration.repoitory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import br.com.airbnb.controller.form.ConsultaForm;
import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Destaque;
import br.com.airbnb.domain.acomodacao.Espaco;
import br.com.airbnb.domain.acomodacao.Hospedes;
import br.com.airbnb.domain.acomodacao.Precificacao;
import br.com.airbnb.domain.acomodacao.QuantidadesComodos;
import br.com.airbnb.domain.acomodacao.TipoLugar;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.repository.AcomodacaoRepository;
import br.com.airbnb.repository.UsuarioRepository;
import br.com.airbnb.service.AcomodacaoService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class AcomodacaoServiceSpecificationTest {

	@Autowired
	private AcomodacaoRepository acomodacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AcomodacaoService acomodacaoService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@BeforeAll
	public void setAcomodacao() {
		Hospedes hospedes = new Hospedes(2, 1, 0, 0);
		Precificacao precificacao = new Precificacao(new BigDecimal("500.00"), null, false);
		QuantidadesComodos quantidadesComodos = new QuantidadesComodos(3, 2);

		String senha = encoder.encode("123456");
		Usuario usuario = new Usuario(null, "First", "Second", null, "ddd@gmail.com", LocalDate.now(), false, false,
				senha, null);

		Usuario usuarioDois = new Usuario(null, "Second", "First", null, "ccc@gmail.com", LocalDate.now(), false, false,
				senha, null);

		usuario = this.usuarioRepository.save(usuario);
		usuarioDois = this.usuarioRepository.save(usuarioDois);

		Acomodacao acomodacao = new Acomodacao(TipoLugar.APARTAMENTO, null, hospedes, null, precificacao,
				new BigDecimal("75.00"), new BigDecimal("150.00"), "Espaço Juremana é um espaço maravilhoso",
				Arrays.asList(Destaque.IDEAL_PARA_FAMILIAS), "Espaço Juremana", usuario, LocalTime.NOON, LocalTime.NOON,
				false, quantidadesComodos, 1);

		var reservaUm = new Reserva(LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(4L),
				LocalDateTime.now(), new Hospedes(2, 0, 0, 0), usuarioDois, acomodacao);

		var reservaDois = new Reserva(LocalDateTime.now().plusDays(5L), LocalDateTime.now().plusDays(7L),
				LocalDateTime.now(), new Hospedes(2, 0, 0, 0), usuarioDois, acomodacao);

		acomodacao.adicionaReserva(reservaUm);
		acomodacao.adicionaReserva(reservaDois);

		this.acomodacaoRepository.save(acomodacao);

		// Segunda acomodação
		Hospedes hospedesDois = new Hospedes(3, 2, 0, 3);
		Acomodacao acomodacaoDois = new Acomodacao(TipoLugar.CASA, null, hospedesDois, null,
				new Precificacao(new BigDecimal("3550.00"), null, false), new BigDecimal("75.00"),
				new BigDecimal("55.00"), "Espaço Julinda", Arrays.asList(Destaque.IDEAL_PARA_FAMILIAS),
				"Espaço Julinda", usuario, LocalTime.NOON, LocalTime.NOON, true, quantidadesComodos, 3);

		this.acomodacaoRepository.save(acomodacaoDois);

		// Terceira acomodação
		Hospedes hospedesTres = new Hospedes(3, 2, 1, 0);
		Acomodacao acomodacaoTres = new Acomodacao(TipoLugar.CASA, null, hospedesTres,
				Arrays.asList(Espaco.AREA_DE_JANTAR_EXTERNA, Espaco.JACUZZI),
				new Precificacao(new BigDecimal("3550.00"), null, false), new BigDecimal("75.00"),
				new BigDecimal("55.00"), "Espaço Julinda",
				Arrays.asList(Destaque.IDEAL_PARA_FAMILIAS, Destaque.TRANQUILA), "Espaço Julinda", usuario,
				LocalTime.NOON, LocalTime.NOON, false, quantidadesComodos, 3);

		this.acomodacaoRepository.save(acomodacaoTres);
	}

	@Test
	void testaConsultaSumarizadaPorPrecoInicialEFinal() {
		ConsultaForm consultaForm = new ConsultaForm(2, null, null, false, null, new BigDecimal("30.00"),
				new BigDecimal("31.00"), null, null, null, null, null, null);
		long consultaSumarizadaResultado = this.acomodacaoService.consultaSumarizada(consultaForm);
		assertEquals(0, consultaSumarizadaResultado);
	}

	@Test
	void testaConsultaSumarizadaPorPorQuantidadeAdultos() {
		ConsultaForm consultaForm = new ConsultaForm(3, null, null, false, null, new BigDecimal("30.00"),
				new BigDecimal("50000.00"), null, null, null, null, null, null);
		long consultaSumarizadaResultado = this.acomodacaoService.consultaSumarizada(consultaForm);
		assertEquals(2, consultaSumarizadaResultado);
	}

	@Test
	void testaConsultaSumarizadaPorTipoAcomodacao() {
		ConsultaForm consultaForm = new ConsultaForm(null, null, null, false, null, new BigDecimal("30.00"),
				new BigDecimal("50000.00"), TipoLugar.CASA, null, null, null, null, null);
		long consultaSumarizadaResultado = this.acomodacaoService.consultaSumarizada(consultaForm);
		assertEquals(2, consultaSumarizadaResultado);
	}

	@Test
	void testaConsultaSumarizadaPorPermissaoAnimal() {
		ConsultaForm consultaForm = new ConsultaForm(null, null, null, false, 3, new BigDecimal("30.00"),
				new BigDecimal("50000.00"), null, null, null, null, null, null);
		long consultaSumarizadaResultado = this.acomodacaoService.consultaSumarizada(consultaForm);
		assertEquals(1, consultaSumarizadaResultado);
	}

	@Test
	void testaConsultaSumarizadaPorAdulto() {
		ConsultaForm consultaForm = new ConsultaForm(2, null, null, false, null, new BigDecimal("30.00"),
				new BigDecimal("50000.00"), null, null, null, null, null, null);
		long consultaSumarizadaResultado = this.acomodacaoService.consultaSumarizada(consultaForm);
		assertEquals(1, consultaSumarizadaResultado);
	}

	@Test
	void testaConsultaSumarizadaPorCriancas() {
		ConsultaForm consultaForm = new ConsultaForm(null, 2, null, false, null, new BigDecimal("30.00"),
				new BigDecimal("50000.00"), null, null, null, null, null, null);
		long consultaSumarizadaResultado = this.acomodacaoService.consultaSumarizada(consultaForm);
		assertEquals(2, consultaSumarizadaResultado);
	}

	@Test
	void testaConsultaSumarizadaPorBebes() {
		ConsultaForm consultaForm = new ConsultaForm(null, null, 1, false, null, new BigDecimal("30.00"),
				new BigDecimal("50000.00"), null, null, null, null, null, null);
		long consultaSumarizadaResultado = this.acomodacaoService.consultaSumarizada(consultaForm);
		assertEquals(1, consultaSumarizadaResultado);
	}

	@Test
	void testaConsultaSumarizadaPorDataNaoOcupada() {
		ConsultaForm consultaForm = new ConsultaForm(null, null, null, false, null, new BigDecimal("30.00"),
				new BigDecimal("50000.00"), null, null, null, null,
				LocalDateTime.now().with(LocalTime.of(12, 00)).plusDays(1L),
				LocalDateTime.now().with(LocalTime.of(12, 00)).plusDays(4L));

		long consultaSumarizadaResultado = this.acomodacaoService.consultaSumarizada(consultaForm);

		ConsultaForm consulta = new ConsultaForm(null, null, null, false, null, new BigDecimal("30.00"),
				new BigDecimal("50000.00"), null, null, null, null,
				LocalDateTime.now().with(LocalTime.of(12, 00)).plusDays(10L),
				LocalDateTime.now().with(LocalTime.of(12, 00)).plusDays(15L));

		long consultaResultadoPeriodoNaoOcupado = this.acomodacaoService.consultaSumarizada(consulta);

		assertEquals(2, consultaSumarizadaResultado);
		assertEquals(3, consultaResultadoPeriodoNaoOcupado);
	}

}
