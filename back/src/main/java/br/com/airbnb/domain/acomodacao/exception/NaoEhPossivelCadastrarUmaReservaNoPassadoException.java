package br.com.airbnb.domain.acomodacao.exception;

public class NaoEhPossivelCadastrarUmaReservaNoPassadoException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
	
	private final String message = "Não é possível cadastrar um reserva no passado";

	@Override
	public String getMessage() {
		return this.message;
	}
}