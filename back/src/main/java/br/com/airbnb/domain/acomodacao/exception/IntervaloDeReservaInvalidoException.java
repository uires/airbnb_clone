package br.com.airbnb.domain.acomodacao.exception;

public class IntervaloDeReservaInvalidoException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	private final String message = "Não é possível cadastrar uma reserva com esse intervalo de data";

	@Override
	public String getMessage() {
		return this.message;
	}
}
