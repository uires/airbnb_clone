package br.com.airbnb.domain.acomodacao.exception;

public class PrecoPernoiteNaoPodeSerMenorQue74ReaisException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	private String message;

	public PrecoPernoiteNaoPodeSerMenorQue74ReaisException() {
		super("Preço do pernoite não pode ser menor que R$ 74,00");
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
