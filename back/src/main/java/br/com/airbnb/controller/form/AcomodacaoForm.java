package br.com.airbnb.controller.form;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.TipoLugar;

public class AcomodacaoForm {
	private TipoLugar lugar;

	public Acomodacao converte() {
		return new Acomodacao(null, lugar, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null);
	}
}
