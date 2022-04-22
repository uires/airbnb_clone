package br.com.airbnb.domain.acomodacao.exception;

public class QuantidadesDeHospedesNaoBateComAcomodacaoException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
	
	private String message;

	public QuantidadesDeHospedesNaoBateComAcomodacaoException() {
		super("Quantidade de hospedes na reserva é maior que o suportado pela acomodação");
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
