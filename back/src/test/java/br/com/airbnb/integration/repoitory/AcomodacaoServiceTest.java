package br.com.airbnb.integration.repoitory;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Destaque;
import br.com.airbnb.domain.acomodacao.Hospedes;
import br.com.airbnb.domain.acomodacao.Precificacao;
import br.com.airbnb.domain.acomodacao.QuantidadesComodos;
import br.com.airbnb.domain.acomodacao.TipoLugar;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.repository.AcomodacaoRepository;
import br.com.airbnb.repository.UsuarioRepository;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class AcomodacaoServiceTest {

	@Autowired
	private AcomodacaoRepository acomodacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeAll
	public void setAcomodacao() {
		Hospedes hospedes = new Hospedes(2, 1, 0, 0);
		Precificacao precificacao = new Precificacao(new BigDecimal("500.00"), null, false);
		QuantidadesComodos quantidadesComodos = new QuantidadesComodos(3, 2);

		String senha = encoder.encode("123456");
		Usuario usuario = new Usuario(null, "First", "Second", null, "ddd@gmail.com", LocalDate.now(), false, false,
				senha, null);

		usuario = this.usuarioRepository.save(usuario);

		Acomodacao acomodacao = new Acomodacao(TipoLugar.APARTAMENTO, null, hospedes, null, precificacao,
				new BigDecimal("75.00"), new BigDecimal("150.00"), "Espaço Juremana é um espaço maravilhoso",
				Arrays.asList(Destaque.IDEAL_PARA_FAMILIAS), "Espaço Juremana", usuario, LocalTime.NOON, LocalTime.NOON,
				false, quantidadesComodos, 1);

		this.acomodacaoRepository.save(acomodacao);
	}

	@Test
	void testaCadastroImagem() throws IOException {
		FileInputStream file = new FileInputStream("/images/belo-400x400.png");
		MockMultipartFile multipart = new MockMultipartFile("file", file);

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		//mockMvc.perform(null).andExpect(null);
	}

}
