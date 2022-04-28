package br.com.airbnb.domain.acomodacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NaoEhPossivelCadastrarUmaReservaNoPassadoException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
	
	private final String message = "Não é possível cadastrar um reserva no passado";

	@Override
	public String getMessage() {
		return this.message;
	}
}