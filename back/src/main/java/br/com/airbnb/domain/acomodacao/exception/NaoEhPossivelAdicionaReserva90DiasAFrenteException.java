package br.com.airbnb.domain.acomodacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NaoEhPossivelAdicionaReserva90DiasAFrenteException extends IllegalArgumentException {
	private static final long serialVersionUID = 3809285311915362220L;

	private final String message = "Não é possível realizar uma reserva 90 dias a frente da data atual";

	@Override
	public String getMessage() {
		return this.message;
	}
}
