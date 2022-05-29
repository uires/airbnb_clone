package br.com.airbnb.controller.auth.form;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.airbnb.domain.usuario.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CadastroForm {

	@NotBlank
	@NotEmpty
	@NotNull
	@Length(max = 255, min = 5)
	private String email;

	@NotBlank
	@NotEmpty
	@NotNull
	@Length(max = 16, min = 8)
	private String senha;

	@NotBlank
	@NotEmpty
	@NotNull
	@Length(max = 16, min = 8)
	private String matchSenha;

	@NotNull
	private String dataNascimento;

	@NotBlank
	@NotEmpty
	@NotNull
	@Length(max = 255, min = 3)
	private String primeiroNome;

	@NotBlank
	@NotEmpty
	@NotNull
	@Length(max = 255, min = 3)
	private String segundoNome;

	@NotNull
	private boolean permiteEmailDeMarketing;

	public Usuario converte() {

		return new Usuario(null, primeiroNome, segundoNome, null, email, LocalDate.parse(dataNascimento),
				permiteEmailDeMarketing, false, senha, null);
	}
}
