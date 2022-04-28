package br.com.airbnb.domain.acomodacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class QuantidadesDeHospedesNaoBateComAcomodacaoException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	private final String message = "Quantidade de hospedes na reserva é maior que o suportado pela acomodação";

	@Override
	public String getMessage() {
		return this.message;
	}
}
