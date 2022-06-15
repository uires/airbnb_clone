package br.com.airbnb.integration.service.usuario;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import br.com.airbnb.controller.exception.EntityNotFoundException;
import br.com.airbnb.domain.usuario.TokenResetSenha;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.domain.usuario.exception.ImpossibilidadeAprovarRegistroException;
import br.com.airbnb.repository.TokenResetSenhaRepository;
import br.com.airbnb.repository.UsuarioRepository;
import br.com.airbnb.service.UsuarioService;
import br.com.airbnb.service.exception.auth.SenhaNaoCoincidemException;
import br.com.airbnb.service.exception.auth.TokenExpiradoException;
import net.bytebuddy.utility.RandomString;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class UsuarioServiceTest {

	@Autowired
	private UsuarioService usuarioService;

	private String tokenUsuarioUm;
	private String tokenUsuarioDois;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TokenResetSenhaRepository tokenResetSenhaRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private String tokenRecuperacaoSenha;
	private String tokenRecuperacaoSenhaExpirado;

	@BeforeAll
	public void criaUsuario() {
		this.tokenUsuarioUm = RandomString.make(155);
		var usuario = new Usuario(null, "First", "Second", null, "ccc123@gmail.com", LocalDate.now(), false, false,
				"123", null, this.tokenUsuarioUm);

		this.tokenUsuarioDois = RandomString.make(155);
		var usuarioDois = new Usuario(null, "First", "Second", null, "bbbc123@gmail.com", LocalDate.now(), false, false,
				"123", null, this.tokenUsuarioDois);

		this.usuarioRepository.save(usuario);
		this.usuarioRepository.save(usuarioDois);

		this.tokenRecuperacaoSenha = RandomString.make(155);
		TokenResetSenha tokenResetSenha = new TokenResetSenha(this.tokenRecuperacaoSenha, LocalDateTime.now(),
				usuarioDois);

		this.tokenRecuperacaoSenhaExpirado = RandomString.make(155);
		TokenResetSenha tokenResetSenhaExpirado = new TokenResetSenha(this.tokenRecuperacaoSenhaExpirado,
				LocalDateTime.now().minusDays(2L), usuarioDois);

		this.tokenResetSenhaRepository.save(tokenResetSenha);
		this.tokenResetSenhaRepository.save(tokenResetSenhaExpirado);
	}

	@Test
	public void testaAprovacaoUsuario() {
		Usuario usuario = this.usuarioService.aprovaRegistro(this.tokenUsuarioUm);
		assertTrue(usuario.isEnabled());
	}

	@Test
	public void testaAprovacaoUsuarioComTokenInexistente() {
		assertThrows(EntityNotFoundException.class, () -> this.usuarioService.aprovaRegistro(RandomString.make(155)));
	}

	@Test
	public void testaAprovacaoDuasVezesDeUmMesmoUsuario() {
		Usuario usuario = this.usuarioService.aprovaRegistro(this.tokenUsuarioDois);
		assertTrue(usuario.isEnabled());
		assertThrows(ImpossibilidadeAprovarRegistroException.class,
				() -> this.usuarioService.aprovaRegistro(this.tokenUsuarioDois));
	}

	@Test
	public void testaReseteSenhasDivergentes() {
		assertThrows(SenhaNaoCoincidemException.class, () -> this.usuarioService.recuperaSenha(this.tokenUsuarioDois,
				this.bCryptPasswordEncoder.encode("123456789"), this.bCryptPasswordEncoder.encode("123459999")));
	}

	@Test
	public void testaLancamentoErrorAoResetarComTokenExpirado() {
		var senha = this.bCryptPasswordEncoder.encode("123456789");
		assertThrows(TokenExpiradoException.class,
				() -> this.usuarioService.recuperaSenha(this.tokenRecuperacaoSenhaExpirado, senha, senha));
	}

	@Test
	public void testaAlteracaoSenha() {
		var senhaAntiga = this.tokenResetSenhaRepository.findByToken(this.tokenRecuperacaoSenha).get().getUsuario()
				.getSenha();

		var senha = "123456789";
		this.usuarioService.recuperaSenha(this.tokenRecuperacaoSenha, senha, senha);

		var senhaNova = this.tokenResetSenhaRepository.findByToken(this.tokenRecuperacaoSenha).get().getUsuario()
				.getSenha();

		assertNotEquals(senhaAntiga, senhaNova);
	}

}
