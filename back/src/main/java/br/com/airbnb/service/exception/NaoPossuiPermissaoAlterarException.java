package br.com.airbnb.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NaoPossuiPermissaoAlterarException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	private final String message = "Não é possível editar este registro";

	@Override
	public String getMessage() {
		return this.message;
	}
}
