package br.com.airbnb.repository.specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Espaco;
import br.com.airbnb.domain.acomodacao.TipoLugar;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;

public class AcomodacaoSpecification {

	public static Specification<Acomodacao> precoNoite(BigDecimal valorInicial, BigDecimal valorFinal) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("precificacao").get("valor"),
				valorInicial, valorFinal);
	}

	public static Specification<Acomodacao> tipoLocal(TipoLugar tipoLugar) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("tipoLugar"), tipoLugar);
	}

	public static Specification<Acomodacao> comodidades(List<Espaco> comodidades) {
		return (root, query, criteriaBuilder) -> root.get("espacos").in(comodidades);
	}

	public static Specification<Acomodacao> permiteAnimais(boolean permiteAnimais) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("permiteAnimais"), permiteAnimais);
	}

	public static Specification<Acomodacao> quantidadeAnimais(Integer quantidadeAnimais) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hopedes").get("animais"),
				quantidadeAnimais);
	}

	public static Specification<Acomodacao> quantidadeBebes(Integer quantidadeBebes) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hopedes").get("bebes"),
				quantidadeBebes);
	}

	public static Specification<Acomodacao> quantidadeCriancas(Integer quantidadeCriancas) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hopedes").get("criancas"),
				quantidadeCriancas);
	}

	public static Specification<Acomodacao> quantidadeAdultos(Integer quantidadeAdultos) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hopedes").get("adultos"),
				quantidadeAdultos);
	}

	public static Specification<Acomodacao> quantidadeQuartosMaiorIgualQueOito(Integer quantidade) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.greaterThanOrEqualTo(root.get("quantidadesComodos").get("quartos"), quantidade);
	}

	public static Specification<Acomodacao> quantidadeQuartorIgual(Integer quantidade) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("quantidadesComodos").get("quartos"),
				quantidade);
	}

	public static Specification<Acomodacao> quantidadeCamasMaiorIgualQueOito(Integer camas) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.greaterThanOrEqualTo(root.get("quantidadesComodos").get("camas"), camas);
	}

	public static Specification<Acomodacao> quantidadeCamasIgual(Integer camas) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("quantidadesComodos").get("camas"),
				camas);
	}

	public static Specification<Acomodacao> quantidadeBanheirosMaiorIgualQueOito(Integer banheiros) {
		return (root, query, criteriaBuilder) -> criteriaBuilder
				.greaterThanOrEqualTo(root.get("quantidadesComodos").get("banheiros"), banheiros);
	}

	public static Specification<Acomodacao> quantidadeBanheirosIgual(Integer banheiros) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("quantidadesComodos").get("banheiros"),
				banheiros);
	}

	public static Specification<Acomodacao> periodoNaoOcupado(LocalDate inicioReserva, LocalDate fimReserva) {
		return (root, query, criteriaBuilder) -> {
			Join<Acomodacao, Reserva> reservaJoin = root.join("reserva");
			criteriaBuilder.between(reservaJoin.get("inicioReserva"), inicioReserva, fimReserva).not();
			return criteriaBuilder.between(reservaJoin.get("fimReserva"), inicioReserva, fimReserva).not();
		};
	}

}
