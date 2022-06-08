package br.com.airbnb.domain.usuario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ImpossibilidadeAprovarRegistroException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	private final String message = "Não é possível ativar um registro já ativo";

	@Override
	public String getMessage() {
		return this.message;
	}
}
