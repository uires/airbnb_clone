package br.com.airbnb.repository.specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import br.com.airbnb.domain.acomodacao.Acomodacao;
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

	public static Specification<Acomodacao> periodoNaoOcupado(LocalDateTime inicioReserva, LocalDateTime fimReserva) {
		return (root, query, criteriaBuilder) -> {
			Join<Acomodacao, Reserva> reservaJoin = root.join("reserva");
			criteriaBuilder.between(reservaJoin.get("inicioReserva"), inicioReserva, fimReserva).not();
			return criteriaBuilder.between(reservaJoin.get("fimReserva"), inicioReserva, fimReserva).not();
		};
	}

}
