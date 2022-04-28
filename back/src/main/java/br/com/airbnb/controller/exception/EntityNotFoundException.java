package br.com.airbnb.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final String message = "Registro não encontrado";

	@Override
	public String getMessage() {
		return this.message;
	}
}