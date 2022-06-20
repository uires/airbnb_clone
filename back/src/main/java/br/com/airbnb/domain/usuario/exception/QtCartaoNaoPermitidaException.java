package br.com.airbnb.domain.usuario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class QtCartaoNaoPermitidaException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	private final String message = "Não é possível cadatrar mais do que três cartões";

	@Override
	public String getMessage() {
		return this.message;
	}
}
