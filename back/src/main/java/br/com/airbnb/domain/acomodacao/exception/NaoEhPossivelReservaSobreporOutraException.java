package br.com.airbnb.domain.acomodacao.exception;

public class NaoEhPossivelReservaSobreporOutraException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	private final String message = "Não é possível realizar uma reserva sobrepõem a data de outra";

	@Override
	public String getMessage() {
		return this.message;
	}
}
