package br.com.airbnb.acomodacao;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Destaque;
import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelCadastrarMaisQueDoisDestaquesException;

public class AcomodacaoTest {

	/**
	 * Válida a estouro de exception ao criar Acomodação mais mais de dois destaques
	 */
	@Test
	public void testaCriacaoAcomodacaoComMaisDeUmDestaque() {
		List<Destaque> destaques = Arrays.asList(Destaque.IDEAL_PARA_FAMILIAS, Destaque.IDEAL_PARA_FAMILIAS,
				Destaque.IDEAL_PARA_FAMILIAS);

		assertThrows(NaoEhPossivelCadastrarMaisQueDoisDestaquesException.class,
				() -> new Acomodacao(null, null, null, null, null, null, null, null, null, null, destaques, null, null,
						null, null, null, null, null, false, null, null));
	}

}
