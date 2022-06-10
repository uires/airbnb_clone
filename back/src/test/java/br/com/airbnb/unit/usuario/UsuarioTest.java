package br.com.airbnb.unit.usuario;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.domain.usuario.exception.ImpossibilidadeAprovarRegistroException;
import net.bytebuddy.utility.RandomString;

public class UsuarioTest {

	private Usuario usuario;

	@BeforeEach
	public void criaUsuario() {
		this.usuario = new Usuario(null, "Aberlado", "Assc", null, "aberlado@g.com", LocalDate.now(), false, false, "",
				null, RandomString.make(155));
	}

	@Test
	public void testaAprovacaoRegistro() {
		this.usuario.aprovaRegistro();
		assertTrue(this.usuario.isEnabled());
	}

	@Test
	public void testaAprovacaoRegistroDuasVezes() {
		this.usuario.aprovaRegistro();
		assertThrows(ImpossibilidadeAprovarRegistroException.class, () -> this.usuario.aprovaRegistro());
	}
}
