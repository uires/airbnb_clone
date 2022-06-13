package br.com.airbnb.integration.service.usuario;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.airbnb.controller.exception.EntityNotFoundException;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.domain.usuario.exception.ImpossibilidadeAprovarRegistroException;
import br.com.airbnb.repository.UsuarioRepository;
import br.com.airbnb.service.UsuarioService;
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
}
