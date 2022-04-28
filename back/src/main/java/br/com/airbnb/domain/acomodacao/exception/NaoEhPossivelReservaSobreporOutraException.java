package br.com.airbnb.domain.acomodacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NaoEhPossivelReservaSobreporOutraException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	private final String message = "Não é possível realizar uma reserva sobrepõem a data de outra";

	@Override
	public String getMessage() {
		return this.message;
	}
}
