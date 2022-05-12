package br.com.airbnb.repository.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.TipoLugar;

public class AcomodacaoSpecification {

	public static Specification<Acomodacao> precoNoite(BigDecimal valorInicial, BigDecimal valorFinal) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("valor"), valorInicial, valorFinal);
	}

	public static Specification<Acomodacao> precoMensal(BigDecimal valorInicial, BigDecimal valorFinal) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("valorMensal"), valorInicial,
				valorFinal);
	}

	public static Specification<Acomodacao> tipoLocal(TipoLugar tipoLugar) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("tipoLugar"), tipoLugar);
	}
}
