package br.com.airbnb.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TipoPgtInvalidoException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	private final String message = "Esse tipo de pagamento com cartão não é válido";

	@Override
	public String getMessage() {
		return this.message;
	}
}