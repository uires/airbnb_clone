package br.com.airbnb.domain.acomodacao.exception;

public class NaoEhPossivelCadastrarMaisQueDoisDestaquesException extends IllegalArgumentException {

	private static final long serialVersionUID = 3809285311915362220L;

	private final String message = "Não é possível cadastrar mais do que dois destaques para um acomodação";

	@Override
	public String getMessage() {
		return this.message;
	}

}
