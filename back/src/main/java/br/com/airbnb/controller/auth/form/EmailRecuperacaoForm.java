package br.com.airbnb.controller.auth.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;

@Getter
public class EmailRecuperacaoForm {

	@NotBlank
	@NotEmpty
	@NotNull
	@Length(max = 255, min = 5)
	private String email;
}
