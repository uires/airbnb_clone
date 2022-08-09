package br.com.airbnb.repository;

import br.com.airbnb.domain.acomodacao.reservas.pagamento.Pagamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
	
	List<Pagamento> findByProcessadoFalse();
}
