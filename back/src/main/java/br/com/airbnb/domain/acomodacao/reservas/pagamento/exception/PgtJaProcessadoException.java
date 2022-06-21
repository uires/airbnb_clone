package br.com.airbnb.domain.acomodacao.reservas.pagamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PgtJaProcessadoException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	private final String message = "Não é possível processar um pagamento já processado";

	@Override
	public String getMessage() {
		return this.message;
	}
}