package br.com.airbnb.controller.auth.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;

@Getter
public class RecuperacaoSenhaForm {

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

}
