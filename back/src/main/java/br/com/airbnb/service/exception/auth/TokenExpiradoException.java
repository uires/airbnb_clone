package br.com.airbnb.service.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TokenExpiradoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final String message = "O token de recuperação é inválido";

	@Override
	public String getMessage() {
		return this.message;
	}
}