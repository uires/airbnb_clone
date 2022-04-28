package br.com.airbnb.domain.acomodacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PrecoPernoiteNaoPodeSerMenorQue74ReaisException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	private final String message = "Preço do pernoite não pode ser menor que R$ 74,00";

	@Override
	public String getMessage() {
		return this.message;
	}

}
