package br.com.airbnb.integration.repoitory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

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
public class AcomodacaoServiceTest {

	@Autowired
	private AcomodacaoRepository acomodacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	private Acomodacao acomodacao;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@BeforeAll
	private void setAcomodacao() {
		Hospedes hospedes = new Hospedes(2, 2, 0, 0);
		Precificacao precificacao = new Precificacao(new BigDecimal("500.00"), null, false);
		QuantidadesComodos quantidadesComodos = new QuantidadesComodos(3, 2);

		String senha = encoder.encode("123456");
		Usuario usuario = new Usuario(null, "First", "Second", null, "ddd@gmail.com", LocalDate.now(), false, false,
				senha, null);
		this.usuarioRepository.save(usuario);

		this.acomodacao = new Acomodacao(TipoLugar.APARTAMENTO, null, hospedes, null, precificacao,
				new BigDecimal("75.00"), new BigDecimal("150.00"), "Espaço Juremana é um espaço maravilhoso",
				Arrays.asList(Destaque.IDEAL_PARA_FAMILIAS), "Espaço Juremana", usuario, LocalTime.NOON, LocalTime.NOON,
				false, quantidadesComodos, 3);

		this.acomodacaoRepository.save(this.acomodacao);
	}

}
