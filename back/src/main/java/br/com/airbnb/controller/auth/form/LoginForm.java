package br.com.airbnb.controller.auth.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Setter;

@Setter
public class LoginForm {

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

	public UsernamePasswordAuthenticationToken converte() {
		return new UsernamePasswordAuthenticationToken(this.email, this.senha);
	}
}
