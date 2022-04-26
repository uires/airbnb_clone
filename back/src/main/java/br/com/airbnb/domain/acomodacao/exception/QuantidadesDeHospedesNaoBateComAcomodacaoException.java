package br.com.airbnb.domain.acomodacao.exception;

public class QuantidadesDeHospedesNaoBateComAcomodacaoException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	private final String message = "Quantidade de hospedes na reserva é maior que o suportado pela acomodação";

	@Override
	public String getMessage() {
		return this.message;
	}
}
